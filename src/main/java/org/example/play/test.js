
function formatCurrency(number) {

    if(number === undefined){
        return 'undefined';
    }

    var currencyCode, userLang;
    var scientific_threshold = 1000000000; //after which value the currency should be formatted in scientific notation?

    if(mathjs.abs(number) < scientific_threshold && !Math.round(number, 2).toString().includes('e+')){
        if (!country || !currencySymbol) {
            return '$' + numberWithCommas(Math.round(number, 2));
        }
        // exclusion from function due to ambiguous currency symbol or not English numeric
        if (manualFormatBefore.includes(country) && country !== 'IN') {
            // currency symbol before the value
            return currencySymbol + ' ' + numberWithCommas(Math.round(number, 2));
        } else if (country === 'IN') {
            // currency symbol for India
            return currencySymbol + ' ' + numberWithCommas(Math.round(number, 0));
        } else if (manualFormatAfter.includes(country)) {
            // currency symbol after the value
            return numberWithCommas(Math.round(number, 2)) + ' ' + currencySymbol;
        } else {
            currencyCode = autoFormatCountries[country][1] || 'USD';
            userLang = autoFormatCountries[country][0] || 'en-US';
            return number.toLocaleString(userLang, {
                style: 'currency',
                currency: currencyCode
            });
        }
    } else {
        var scientific_notation;
        if(!Math.round(number, 2).toString().includes('e+')){
            scientific_notation = number.toExponential().split('e+');
        } else {
            scientific_notation = Math.round(number, 2).toString().split('e+');
        }

        var minus = '';

        if(scientific_notation[0].includes('-')){
            scientific_notation[0] = scientific_notation[0].substring(1);
            minus = '-';
            number = -1 * number;
        }

        //show only two decimal places
        scientific_notation[0] = mathjs.round(Number(scientific_notation[0]), 2).toString();
        if (!country || !currencySymbol) {
            return minus + '$' + scientific_notation[0] + '*10' + sup(scientific_notation[1]);
        }
        // exclusion from function due to ambiguous currency symbol or not English numeric
        if (manualFormatBefore.includes(country)) {
            // currency symbol before the value
            return minus + currencySymbol + scientific_notation[0] + '*10' + sup(scientific_notation[1]);
        } else if (manualFormatAfter.includes(country)) {
            // currency symbol after the value
            return minus + scientific_notation[0] + '*10' + sup(scientific_notation[1]) + currencySymbol;
        } else {
            currencyCode = autoFormatCountries[country][1] || 'USD';
            userLang = autoFormatCountries[country][0] || 'en-US';
            var new_currencySymbol = (0).toLocaleString(userLang, {
                style: 'currency',
                currency: currencyCode,
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
            }).replace(/\d/g, '').trim();

            var is_currency_before = isNaN(Number(number.toLocaleString(userLang, {
                style: 'currency',
                currency: currencyCode
            })[0]));

            if(is_currency_before){
                return minus + new_currencySymbol + scientific_notation[0].toLocaleString(userLang) + '*10' + sup(scientific_notation[1]);
            } else {
                return minus + scientific_notation[0].toLocaleString(userLang) + '*10' + sup(scientific_notation[1]) + new_currencySymbol;
            }
        }
    }

    //extra function for superscript generation
    function sup(number){
        if(number === undefined){return 'ᴺᵃᴺ';}
        return number.toString().replace(/./g, superscriptFromDigit);
        function superscriptFromDigit(singleNumber) {
            var char = "⁰¹²³⁴⁵⁶⁷⁸⁹".charAt(singleNumber);
            if(char !== '') { return char; }
            else { return singleNumber; }
        }
    }
}

function numberWithCommas(x) {

    if (country !== 'IN') {

        return x = x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')

    } else {

        x = mathjs.round(x, 0)
        x = x.toString()
        var lastThree = x.substring(x.length-3)
        var otherNumbers = x.substring(0,x.length-3)
        if(otherNumbers !== '')
        {lastThree = ',' + lastThree}

        return x = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree

    }
}

var manualFormatBefore = [
    'BH','BJ','BT','CN','EG','ET','GH','GN','IS','IQ','IL','JO','KW','LB','MV','MN','MZ',
    'MM','NA','NP','OM','PH','QA','SA','SD','CH','SY','TG','TM','UG','UY','UZ','YE',
    'ZW','IN'
];

var manualFormatAfter = [
    'AF','BD','BY','BF','TD','KM','DJ','ER','GW','IR','ML','MR','NE','RO','ST','SN',
    'RS','SO','UA','AE'
];

var autoFormatCountries = {
    RU: ['ru-RU', 'RUB']
};