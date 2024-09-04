"use strict";

//author: tibor pal

let onResulCallBack = function (ctx, _VarMode) {

    var VarMode = _VarMode.toNumber();

    ctx.hideVariables('t', 'Payment', 'Pmt', 'Per')

    if (VarMode === 1) {

        ctx.showVariables('t', 'Pmt')
    } else {

        ctx.showVariables('Payment', 'Per')
    }

};
omni.onResult(
    ['VarMode'],
    onResulCallBack
);

var country = 'US';
var currencySymbol = '$';
omni.onInit(function (ctx) {
    country = ctx.getCountryCode() || 'US'
});

var DATA11 = [
    {name: 'loan term', uid: '1', values: {VarMode: 1}},
    {name: 'monthly payment', uid: '2', values: {VarMode: 2}},
];

omni.createValueSetter('Mode', DATA11, {defaultUid: '1',});

var DATA01 = [
    {name: 'Capitalized (added to principal)', uid: '11', values: {VarInt: 1}},
    {name: 'Paid monthly during deferment', uid: '112', values: {VarInt: 11}},
    {name: 'Paid in equal parts (accum. separately)', uid: '12', values: {VarInt: 2}},
    {name: 'Interest-free', uid: '13', values: {VarInt: 3}},
];

omni.createValueSetter('DefIntMode', DATA01, {defaultUid: '11',});

var DATA02 = [
    {name: 'increased payment - original term', uid: '11', values: {VarDef: 1}},
    {name: 'increased payment - extended term', uid: '12', values: {VarDef: 2}},
    {name: 'original payment - extended term', uid: '13', values: {VarDef: 3}},
];

omni.createValueSetter('DefType', DATA02, {defaultUid: '11',});

var DATA1 = [
    {name: 'Chart of balances', uid: '11', values: {VarRep: 1}},
    {name: 'Table of payment schedule', uid: '12', values: {VarRep: 2}},
    {name: 'Both', uid: '13', values: {VarRep: 3}},
];

omni.createValueSetter('represent', DATA1, {defaultUid: '11',});

var DATA2 = [
    {name: 'Monthly schedule', uid: '111', values: {VarTable: 1}},
    {name: 'Annual schedule', uid: '112', values: {VarTable: 2}},
];

omni.createValueSetter('TableFreq', DATA2, {defaultUid: '111',});

var DATA3 = [
    {name: 'Annually', uid: '11', values: {m: 1,}},
    {name: 'Semi-annually', uid: '12', values: {m: 2,}},
    {name: 'Quarterly', uid: '13', values: {m: 4,}},
    {name: 'Monthly', uid: '14', values: {m: 12,}},
    {name: 'Daily', uid: '15', values: {m: 365.25,}},
];

omni.createValueSetter('CompoundFreq', DATA3, {defaultUid: '14',});

let onInitSetup = function (ctx) {

    var milisecondsInADay = 24 * 60 * 60 * 1000;

    //omni.onInit(function(ctx) {
    //var date = new Date();
    ////date.setFullYear(date.getFullYear());
    //ctx.setDefault('DefFrom', date.getTime() / milisecondsInADay);
    //});

    omni.onInit(function (ctx) {
        var date = new Date();
        date.setFullYear(date.getFullYear() + 0);
        ctx.setDefault('DefTill', date.getTime() / milisecondsInADay);
    });

    ctx.setDefault("first_day", Math.floor(new Date().getTime() / milisecondsInADay).toString());

    ctx.setDefault("DefFrom", Math.floor(new Date().getTime() / milisecondsInADay).toString());

    //ctx.setDefault("DefFrom", Math.floor(new Date().getTime() / milisecondsInADay).toString());
    //ctx.setDefault("DefTill", Math.floor(18627).toString());

};
omni.onInit(onInitSetup);

let callBackOmniOnResultIDKWhatItDoesButItSeemsLikeMainFunction = function (
    ctx, _r, _t, _m, _q, _LoanAmount, _VarTable, _VarRep, _first_day, _DefFrom, _DefTill, _VarInt, _VarDef, _Payment, _VarMode, _DefPer
) {

    var r = _r.toNumber();
    var t = _t.toNumber();
    var m = _m.toNumber();
    var q = _q.toNumber();
    var LoanAmount = _LoanAmount.toNumber();
    var VarTable = _VarTable.toNumber();
    var VarRep = _VarRep.toNumber();
    var first_day = _first_day.toNumber();
    var DefFrom = _DefFrom.toNumber();
    var DefTill = _DefTill.toNumber();
    var VarInt = _VarInt.toNumber();
    var VarDef = _VarDef.toNumber();
    var Payment = _Payment.toNumber();
    var VarMode = _VarMode.toNumber();
    var DefPer = _DefPer.toNumber();

    //   VarDef = 2;

    var DefInt = 0
    var DefIntRef = 0

    var milisecondsInADay = 24 * 60 * 60 * 1000

    currencySymbol = ctx.getCurrencySymbol() || '$'

    //currencySymbol = '₹'

    var DefDateTill = getDateGapEnd(first_day, DefFrom, DefPer)

    //ctx.addHtml(DefDateTill,{afterVariable:'r'})

    // Additional variables

    var periods = q * t

    var pmt
    var pmtBefDef
    var totalPmt

    var eq_p = getPerEqRate(q, m, r)

    if (VarMode === 1) {

        pmt = getPmt(LoanAmount, periods, eq_p)
        pmtRef = pmt = getPmt(LoanAmount, periods, eq_p)

        Payment = 0

        pmtBefDef = pmt

    }

    var Per = periods

    if (VarMode === 2) {

        pmt = Payment
        pmtRef = Payment

        pmtBefDef = Payment

        Per = getPer(LoanAmount, Payment, eq_p)

        periods = Per;

    }
    if (Per > 0) {

        hide = 0;

    } else {

        hide = 1;
        ctx.addHtml('<b><i>The estimated term is too short or long for displaying the result - please adjust the parameters.</b></i>', {afterVariable: 'r'});

        ctx.hideVariables('Per');

    }

    if (LoanAmount > Payment) {

        hide = 0;

    } else {

        hide = 1;
        ctx.addHtml('<b><i>The loan amount must be larger than the given payment.</b></i>', {afterVariable: 'r'});

        ctx.hideVariables('Per');

    }

    var pmtRef = pmt;

    var hide = 0
    var hide2 = 0

    if (VarDef === 1 && periods <= DefPer) {
        hide2 = 1

        ctx.addHtml('The number of deferment periods should be less than the loan term if you choose the "increased payments - same term" option.', {afterVariable: 'DefPer'});

    }

    if (hide2 === 0) {

        ctx.showVariables('represent');

        if (Per > 0) {

            hide = 0;

        } else {

            hide = 1;
            ctx.addHtml('<b><i>The monthly payment is too small or large compared to the loan amount - please update the loan inputs.</b></i>', {afterVariable: 'r'});

            ctx.hideVariables('Per');

        }

        if (LoanAmount / Payment < 2 && VarMode === 2) {

            hide = 1;
            ctx.addHtml('<b><i>The monthly payment is too large compared to the loan amount - please update the loan inputs.</b></i>', {afterVariable: 'r'});

            ctx.hideVariables('Per');

        }

        var GapTillDef = getGapPeriods(first_day, first_day, DefFrom - 1) //corrected by minus one day

        var BalanceAtDef = LoanAmount * mathjs.pow(1 + eq_p, GapTillDef) - pmtBefDef / eq_p *
            (mathjs.pow(1 + eq_p, GapTillDef) - 1)

        if (BalanceAtDef <= 0) {

            hide = 1;
            ctx.addHtml('<b><i>With the given dates, your loan will already be paid off at the beginning of deferment.</b></i>', {afterVariable: 'VarRep'});

        }

        if (BalanceAtDef < 0) {
            BalanceAtDef = 0
        }

        if (VarInt === 1 || VarInt === 2) {
            DefInt = (BalanceAtDef * mathjs.pow(1 + eq_p, DefPer)) - BalanceAtDef
        }

        if (VarInt === 11 || VarInt === 3) {
            DefInt = 0
        }

        var PerAftDef

        //ctx.addHtml(GapTillDef,{afterVariable:'q'})

        if (VarDef === 1) {
            PerAftDef = periods - GapTillDef - DefPer
            pmt = getPmt(BalanceAtDef + DefInt, PerAftDef, eq_p)
        }
        if (VarDef === 2) {
            PerAftDef = periods - GapTillDef
            pmt = getPmt(BalanceAtDef + DefInt, PerAftDef, eq_p)
        }
        if (VarDef === 3) {
            PerAftDef = getPer(BalanceAtDef + DefInt, pmt, eq_p)
            if (PerAftDef < 0.1) {
                hide = 1, hide2 = 1
                ctx.addHtml('With the given setup, you can\'t repay your loan after the deferment.', {afterVariable: 'VarRep'})
            }

        }

        var DefIntEq = 0

        if (VarDef === 3 && VarInt === 2) {

            PerAftDef = getNewPer(BalanceAtDef, eq_p, periods - GapTillDef, DefInt, pmt)
        }

        if (VarInt === 2) {

            DefIntEq = DefInt / PerAftDef
        }

        //var DefIntPerBef = 0;

        //if (VarInt === 1) {DefIntPerBef = DefIntPer}

        var DefIntPerAft = 0;

        if (VarInt === 2) {
            DefIntPerAft = DefIntEq
        }

        // Balances and charts

        var daysPassed = 0;
        var daysInPeriod

        var dd;
        var mm;
        var yyyy;
        var date_table;
        var date;

        // *** //


        var periodsMonth = periods + DefPer + PerAftDef

        var rMonth = eq_p

        var outTableYear = [];
        var outTableMonth = [];
        var outTableYearEq = [];
        var outTableMonthEq = [];
        var outChart1 = [];
        var outChart2 = [];

        var i;
        var rowLabelChart;
        var pointFractionChart = 0;

        var intYear = 0;
        var princYear = 0;
        var intYearRef = 0;
        var princYearRef = 0;

        var pmtMonth = pmt;
        var pmtMonthRef = pmtRef;

        var princBal = LoanAmount;
        var princBalRef = LoanAmount;

        var princBalOpen;
        var princMonth;
        var intMonth = 0;
        var princPaid = 0;
        var intPaid = 0;

        var princBalOpenRef = 0;
        var princMonthRef = 0;
        var intMonthRef = 0;
        var princPaidRef = 0;
        var intPaidRef = 0;

        //var periodCount;

        var yearChart = 0;

        var princBalOpenYear = 0;
        var princBalOpenYearRef = 0;

        var LoopDate;
        var DefIntMonth = 0;
        var DefIntMonthRef = 0;
        var DefIntYear = 0;
        var DefIntTot = 0;
        var DefIntYearRef = 0;
        var DefIntTotRef = 0;

        var DefIntPeriodRef = DefIntRefPer;
        var DefIntPaid = 0;
        var DefIntPaidRef = 0;

        var iCurr = 0;
        var iRef = 0;
        var PerSumCurr = 0;
        var PerSumRef = 0;
        var DateChart = 0;
        var DateChartRef = 0;

        var DefDateFrom = formatDate(DefFrom)
        //var DefDateTill = formatDate(DefTill)

        var TotalPmtSum = 0

        //ctx.addHtml(DefDateTill,{afterVariable:'represent'});

        for (i = 0; i <= periodsMonth; i++) { // loop from here

            date = new Date((first_day + daysPassed) * milisecondsInADay)

            daysInPeriod = getDaysInPeriod(first_day, date, q)

            if (i > 0) {

                daysPassed += daysInPeriod

                dd = date.getDate();
                mm = date.getMonth() + 1;
                yyyy = date.getFullYear();

                var mm2;

                if (mm === 1) {
                    mm2 = 'Jan.';
                }
                if (mm === 2) {
                    mm2 = 'Feb.';
                }
                if (mm === 3) {
                    mm2 = 'Mar.';
                }
                if (mm === 4) {
                    mm2 = 'Apr.';
                }
                if (mm === 5) {
                    mm2 = 'May.';
                }
                if (mm === 6) {
                    mm2 = 'Jun.';
                }
                if (mm === 7) {
                    mm2 = 'Jul.';
                }
                if (mm === 8) {
                    mm2 = 'Aug.';
                }
                if (mm === 9) {
                    mm2 = 'Sept.';
                }
                if (mm === 10) {
                    mm2 = 'Oct.';
                }
                if (mm === 11) {
                    mm2 = 'Nov.';
                }
                if (mm === 12) {
                    mm2 = 'Dec.';
                }

                if (dd < 10) {
                    dd = '0' + dd;
                }
                if (mm < 10) {
                    mm = '0' + mm;
                }
                date_table = mm + '.' + yyyy;

                //rowLabelTable = date_table;
                rowLabelChart = date_table;

                var dateFormat = mm2 + ' ' + dd + ', ' + yyyy;

                LoopDate = yyyy + '' + mm + '' + dd;

                // *** //

                DefIntPeriodRef = 0
                intMonthRef = princBalRef * rMonth; //ok
                princMonthRef = pmtMonthRef - intMonthRef; //ok
                princBalRef = princBalRef - princMonthRef; //ok
                princBalOpenRef = princBalRef + princMonthRef; //ok

                if (LoopDate >= DefDateFrom && LoopDate <= DefDateTill) {
                    //if (LoopDate > DefDateFrom && i <= GapTillDef + DefPer){

                    if (VarInt !== 3) {
                        intMonth = princBal * eq_p
                    } else {
                        intMonth = 0
                    }


                    if (VarInt === 1) {
                        DefIntMonth = princBal * eq_p
                    }
                    //intMonth = DefIntPerBef
                    princMonth = 0
                    princBal = princBal - princMonth + DefIntMonth
                    princBalOpen = princBal + princMonth - DefIntMonth


                    if (VarInt === 11) {
                        totalPmt = intMonth
                    } else {
                        totalPmt = 0
                    }

                }

                if (LoopDate > DefDateTill) {
                    //if (LoopDate >= DefDateFrom && i > GapTillDef + DefPer){

                    //DefIntPeriod = DefIntPer
                    intMonth = princBal * rMonth; //ok

                    if (VarDef === 3) {
                        princMonth = pmtMonth - intMonth - DefIntEq
                    } else {
                        princMonth = pmtMonth - intMonth
                    }

                    princBal = princBal - princMonth; //ok
                    princBalOpen = princBal + princMonth; //ok

                    if (VarInt === 2 && princMonth > 0) {

                        totalPmt = princMonth + intMonth + DefIntEq;
                    } else {
                        totalPmt = princMonth + intMonth;
                    }


                }

                if (LoopDate < DefDateFrom) {

                    //DefIntPerBef = 0
                    intMonth = princBal * rMonth; //ok
                    princMonth = pmtBefDef - intMonth; //ok
                    princBal = princBal - princMonth; //ok
                    princBalOpen = princBal + princMonth; //ok

                    if (VarInt === 2 && princMonth > 0) {

                        totalPmt = princMonth + intMonth + DefIntEq;
                    } else {
                        totalPmt = princMonth + intMonth;
                    }

                }

                if (princMonth > 0 && VarInt === 2) {
                    DefIntPerAft = DefIntEq
                } else {
                    DefIntPerAft = 0
                }

                if (i === 0) {
                    intMonth = 0;
                    princMonth = 0;
                    intMonthRef = 0;
                    princMonthRef = 0;
                }

                if (princBalOpen < pmtMonth) {
                    princMonth = princBalOpen;
                }
                if (princBalOpenRef < pmtMonthRef) {
                    princMonthRef = princBalOpenRef;
                }

                if (princBal <= 0) {
                    princBal = 0;
                }
                if (princBalOpen <= 0) {
                    princBalOpen = 0;
                    DefIntPerAft = 0
                }
                if (princBalOpen <= 0) {
                    intMonth = 0;
                }
                if (princMonth <= 0) {
                    princMonth = 0;
                }
                if (intMonth <= 0) {
                    princMonth = 0;
                }
                if (princBal <= 0) {
                    princPaid = LoanAmount;
                    ;totalPmt = intMonth + DefIntEq
                }

                if (princBalRef <= 0) {
                    princBalRef = 0;
                }
                if (princBalOpenRef <= 0) {
                    princBalOpenRef = 0
                }
                if (princBalOpenRef <= 0) {
                    intMonthRef = 0;
                }
                if (princMonthRef <= 0) {
                    princMonthRef = 0;
                }
                if (intMonthRef <= 0) {
                    princMonthRef = 0;
                }
                if (princBalRef <= 0) {
                    princPaidRef = LoanAmount
                }

                /*if (princBal < 0.001 && princBalOpen > 0)
                {DefIntPeriod = DefInt - DefIntPaid}
                if (princBalRef < 0.001 && princBalOpenRef > 0)
                {DefIntPeriodRef = DefIntRef - DefIntPaidRef}

                */

                DefIntTot += DefIntMonth;
                DefIntPaid += DefIntPerAft;

                intPaid += intMonth;
                princPaid += princMonth;
                intPaidRef += intMonthRef;
                princPaidRef += princMonthRef;

                if (mm === '01') {
                    intYear = 0;
                    princYear = 0;
                    DefIntYear = 0;
                }
                if (mm === 12 || princBal < 0.01) {
                    yearChart = 1;
                } else {
                    yearChart = 0;
                }

                intYear += intMonth;
                princYear += princMonth;
                DefIntYear += DefIntPerAft;

                intYearRef += intMonthRef;
                princYearRef += princMonthRef;

                princBalOpenYear = princBal + princYear;
                princBalOpenYearRef = princBalRef + princYearRef;

                if (princBalOpen > 0.001 && princMonth > 0.001) {
                    iCurr = 1
                } else {
                    iCurr = 0
                }
                if (princBalOpenRef && princMonthRef > 0.001) {
                    iRef = 1
                } else {
                    iRef = 0
                }

                if (princBalOpen > 0.001 && princBal <= 0.001) {
                    DateChart = dateFormat
                }
                if (princBalOpenRef > 0.001 && princBalRef <= 0.001) {
                    DateChartRef = dateFormat
                }

                PerSumCurr += iCurr
                PerSumRef += iRef

                TotalPmtSum += totalPmt

            }

            var HEADER_TableMonth = ["Month",
                "Opening bal.",
                "Principal",
                "Interest",
                "Payment",
                "Closing bal.",];

            var HEADER_TableYear = ["Year",
                "Opening bal.",
                "Principal",
                "Interest",
                "Payment",
                "Closing bal.",];

            var HEADER_TableMonthEq = ["Month",
                "Opening bal.",
                "Principal",
                "Interest",
                "Def. int.",
                "Payment",
                "Closing bal.",];

            var HEADER_TableYearEq = ["Year",
                "Opening bal.",
                "Principal",
                "Interest",
                "Def. int.",
                "Payment",
                "Closing bal.",];

            var HEADER_Chart = ["",
                "Princ. w/o def.",
                , ,
                "Princ. with def.",
                , , ,
                "Int. w/o def.",
                "Int. with def.",];

            if (VarInt !== 2) {

                if (i > 0 && princBalOpen > 0.001) {

                    outTableMonth.push([
                        date_table,
                        //   LoopDate,
                        //   DefDateFrom,DefDateTill,
                        // i,
                        numberWithCommas(mathjs.round(princBalOpen, 2)),
                        numberWithCommas(mathjs.round(princMonth, 2)), //ok
                        numberWithCommas(mathjs.round(intMonth, 2)), //ok
                        numberWithCommas(mathjs.round(totalPmt, 2)),
                        numberWithCommas(mathjs.round(princBal, 2)), //ok
                    ]);
                }

                if ((yearChart === 1 && princBalOpen > 0) || (princBalOpen > 0.01 && princBal < 0.01)) {

                    outTableYear.push([
                        yyyy,
                        numberWithCommas(mathjs.round(princBalOpenYear, 2)),
                        numberWithCommas(mathjs.round(princYear, 2)), //ok
                        numberWithCommas(mathjs.round(intYear, 2)), //ok
                        numberWithCommas(mathjs.round(princYear + intYear, 2)),
                        numberWithCommas(mathjs.round(princBal, 2)),
                    ]);
                }
            }

            if (VarInt === 2) {

                if (i > 0 && princBalOpen > 0.001) {

                    outTableMonthEq.push([
                        //date_table,
                        i,
                        numberWithCommas(mathjs.round(princBalOpen, 2)),
                        numberWithCommas(mathjs.round(princMonth, 2)), //ok
                        numberWithCommas(mathjs.round(intMonth, 2)), //ok
                        numberWithCommas(mathjs.round(DefIntPerAft, 2)), //ok
                        numberWithCommas(mathjs.round(totalPmt, 2)),
                        numberWithCommas(mathjs.round(princBal, 2)), //ok
                    ]);
                }

                if ((yearChart === 1 && princBalOpen > 0) || (princBalOpen > 0.01 && princBal < 0.01)) {

                    outTableYearEq.push([
                        yyyy,
                        numberWithCommas(mathjs.round(princBalOpenYear, 2)),
                        numberWithCommas(mathjs.round(princYear, 2)), //ok
                        numberWithCommas(mathjs.round(intYear, 2)), //ok
                        numberWithCommas(mathjs.round(DefIntYear, 2)), //ok
                        numberWithCommas(mathjs.round(princYear + intYear + DefIntYear, 2)),
                        numberWithCommas(mathjs.round(princBal, 2)),
                    ]);
                }
            }


            //if (pointFractionChart < i + 1 && t <= 2) {

            if ((t <= 2) &&
                (princBalOpen > 0.0001 || princBalOpenRef > 0.0001) ||
                (princBalOpen > 0.0001 && princBal < 0.0001 && princBalOpenRef < 0.0001) ||
                (princBalOpenRef > 0.0001 && princBalRef < 0.0001 && princBalOpen < 0.0001) ||
                (princBalOpenRef > 0.0001 && princBalRef < 0.0001 && princBalOpen > 0.0001 &&
                    princBal < 0.0001) ||
                (i === 0)
            ) {

                outChart1.push([

                    rowLabelChart,
                    (mathjs.round(princBalRef, 0)),
                    , ,
                    (mathjs.round(princBal, 0)),
                    , , ,
                    (mathjs.round(intPaidRef, 0)),
                    (mathjs.round(intPaid, 0)),


                ]);

            }

            if ((pointFractionChart < i && t > 2) &&
                (princBalOpen > 0.0001 || princBalOpenRef > 0.0001) ||
                (princBalOpen > 0.0001 && princBal < 0.0001 && princBalOpenRef < 0.0001) ||
                (princBalOpenRef > 0.0001 && princBalRef < 0.0001 && princBalOpen < 0.0001) ||
                (princBalOpenRef > 0.0001 && princBalRef < 0.0001 && princBalOpen > 0.0001 &&
                    princBal < 0.0001) ||
                (i === 0)
            ) {

                outChart2.push([

                    rowLabelChart,
                    (mathjs.round(princBalRef, 0)),
                    , ,
                    (mathjs.round(princBal, 0)),
                    , , ,
                    (mathjs.round(intPaidRef + DefIntPaidRef, 0)),
                    (mathjs.round(intPaid + DefIntPaid, 0)),

                ]);

                rowLabelChart++;
                pointFractionChart += 12;

            }
        }


        if (hide !== 1) {

            //ctx.addHtml('A <b>moratórium alatt</b> nem teljesített <b>kamat és díjtartozás összege '+formatCurrency(DefInt) +'</b>, amelynek <b>törlesztése havonta</b>, <i>a moratórium végét követő meghosszabbított futamidő alatt</i>, <b>'+ formatCurrency(DefIntPer) +'</b> egyenlő összegű részletben történik.',{afterVariable:'VarRep'});

            if (VarInt === 2) {

                ctx.addHtml('The accumulated <b>interest during deferment</b> is <b>' + formatCurrency(DefInt) + '</b>, which is <b>paid back</b> after deferment ends by <b>equal payments of <b>' + formatCurrency(DefIntEq) + '</b>.', {afterVariable: 'VarRep'});
            }


            if (VarInt === 1) {

                ctx.addHtml('The <b>loan balance is ' + formatCurrency(BalanceAtDef) + '</b> at the beginning, which will become <b>' + formatCurrency(BalanceAtDef + DefInt) + '</b> at the end of the deferment.', {afterVariable: 'VarRep'});

                ctx.addHtml('The accumulated <b>interest during the deferment</b> is <b>' + formatCurrency(DefInt) + '</b>.', {afterVariable: 'VarRep'});

            }

            if (VarInt === 11) {

                ctx.addHtml('The <b>loan balance is ' + formatCurrency(BalanceAtDef) + '</b> at the beginning, and will remain the same at the end of the deferment.', {afterVariable: 'VarRep'});

                ctx.addHtml('The <b>monthly interest payment during the deferment</b> is <b>' + formatCurrency(BalanceAtDef * eq_p) + '</b>.', {afterVariable: 'VarRep'});

            }


            //ctx.addHtml('Check '+formatCurrency(DefInt-DefIntPaid) +'</b>',{afterVariable:'VarRep'});

            if (VarInt !== 2) {

                if (VarTable === 1 && (VarRep === 3 || VarRep === 2)) {
                    ctx.addTable(outTableMonth, HEADER_TableMonth, {afterVariable: 'VarMode'});
                }

                if (VarTable === 2 && (VarRep === 3 || VarRep === 2)) {
                    ctx.addTable(outTableYear, HEADER_TableYear, {afterVariable: 'VarMode'})
                }
            }

            if (VarInt === 2) {

                if (VarTable === 1 && (VarRep === 3 || VarRep === 2)) {
                    ctx.addTable(outTableMonthEq, HEADER_TableMonthEq, {afterVariable: 'VarMode'});
                }

                if (VarTable === 2 && (VarRep === 3 || VarRep === 2)) {
                    ctx.addTable(outTableYearEq, HEADER_TableYearEq, {afterVariable: 'VarMode'})
                }
            }


            if (((VarRep === 1) || (VarRep === 3))) {

                if (t <= 2) {

                    ctx.addChart({

                        type: 'line',
                        data: outChart1,
                        labels: HEADER_Chart.slice(0, 20),
                        afterVariable: 'represent',
                        // title: 'Monthly balances',

                    });

                } else {

                    ctx.addChart({

                        type: 'line',
                        data: outChart2,
                        labels: HEADER_Chart.slice(0, 20),
                        afterVariable: 'represent',
                        // title: 'Monthly balances',

                    });

                }
            }

            var TermCurr = 0;
            var TermRef = 0;
            var TermDiff = 0;
            var TermDiff2 = 0;


            var Gap = 0
            //var DefIntPer = 0
            var DefIntRefPer = 0


            //var PmtNoAdj = 0

            //if (VarDef !== 1) {PmtNoAdj = DefPer}


            var perCurr = mathjs.ceil(PerSumCurr);
            var perRef = mathjs.ceil(PerSumRef);
            var perDiff = Math.abs(PerSumCurr - PerSumRef);
            var perDiff2 = Math.abs(PerSumCurr + DefPer - PerSumRef);


            TermCurr = formatTerm(perCurr)
            TermRef = formatTerm(perRef)
            TermDiff = formatTerm(perDiff)
            TermDiff2 = formatTerm(perDiff2)


            var TermDiff2b = ' (' + (perCurr - perRef + mathjs.round(Gap)) + ' months)'
            var TermDiff1b = ' (' + (perCurr - perRef) + ' payments)'
            if ((perCurr - perRef + mathjs.round(Gap)) < 0.01) {
                TermDiff2b = ''
            }

            if (perCurr - perRef + mathjs.round(Gap) < 0.01) {
                TermDiff1b = ' (' + -(perCurr - perRef) + ' payment less)'
            }

            if ((perCurr - perRef + mathjs.round(Gap) < 0.01) && VarDef == 2) {
                TermDiff1b = ''
            }

            //var pmtDiff = ' ('+(perCurr-perRef)+' payments)';

            var EMIDiff = formatCurrency(mathjs.abs((pmt + DefIntEq) - (pmtRef)))
            if ((mathjs.abs((pmt + DefIntEq) - (pmtRef))) < 0.01) {
                EMIDiff = 'no change'
            }

            var IntDiff = formatCurrency(mathjs.abs((intPaid + DefInt) - intPaidRef))
            if ((mathjs.abs(intPaid - intPaidRef)) < 0.01) {
                IntDiff = 'no change'
            }

            if (TermDiff === 0) {
                TermDiff = 'no change'
            }
            if (TermDiff2 === 0) {
                TermDiff2 = 'no change'
            }

            var outTableResult = [];

            var Title;
            var Curr;
            var Ref;
            var Diff;

            if (VarDef === 3) {
                DefIntEq = 0
            }
            //if (VarInt === 1) {DefInt = 0}

            if (VarDef === 2) {
                DefInt = 0
            }

            var DiffText = ''
            var DiffText2 = ' more'

            if ((intPaid + DefInt) - intPaidRef > 0.01) {
                DiffText = ' more'
            }
            if ((intPaid + DefInt) - intPaidRef < -0.01) {
                DiffText = ' less'
            }

            if ((mathjs.abs((pmt + DefIntEq) - (pmtRef))) < 0.01) {
                DiffText2 = ''
            }

            var ForLoop = 5

            for (i = 0; i <= ForLoop; i++) {

                switch (i) {
                    case 0:
                        Title = '<small><table border=1><tr><td></td>';
                        Curr = '<td><b>Without deferment</b></td>';
                        Ref = '<td><b>With deferment*</b></td>';
                        Diff = '<td><b>Difference</b></td></tr>';
                        break;
                    case 1:
                        Title = '<tr><td><b>Monthly payment</b></td>';
                        Curr = '<td>' + formatCurrency(pmtRef) + '</td>';
                        Ref = '<td>' + formatCurrency(pmt + DefIntEq) + '</td>';
                        Diff = '<td>' + EMIDiff + DiffText2 + '</td></tr>';
                        break;
                    case 2:
                        Title = '<tr><td><b>Interest payment</b></td>';
                        Curr = '<td>' + formatCurrency(intPaidRef) + '</td>';
                        Ref = '<td>' + formatCurrency(intPaid + DefInt) + '</td>';
                        Diff = '<td>' + IntDiff + DiffText + '</td></tr>';
                        break;
                    /*    case 3:
                        Title = '<tr><td><b>deferment interest</b></td>';
                  Curr =  '<td>'+formatCurrency(0)+'</td>';
                  Ref =   '<td>'+formatCurrency(DefInt)+'</td>';
                  Diff =  '<td>'+formatCurrency(mathjs.abs((0 + DefInt)-(0)))+DiffText+'</td></tr>';
                          break;*/
                    case 3:
                        Title = '<tr><td><b>Total payment</b></td>';
                        Curr = '<td>' + formatCurrency(LoanAmount + intPaidRef) + '</td>';
                        Ref = '<td>' + formatCurrency(LoanAmount + intPaid + DefInt) + '</td>';
                        Diff = '<td>' + IntDiff + DiffText + '</td></tr>';
                        break;
                    case 4:
                        Title = '<tr><td><b>Term with pmt</b></td>';
                        Curr = '<td>' + TermRef + ' (' + perRef + ' payments)</td>';
                        Ref = '<td>' + TermCurr + ' (' + perCurr + ' payments)</td>';
                        Diff = '<td>' + TermDiff + TermDiff1b + '</td></tr>';
                        break;
                    case 5:
                        Title = '<tr><td><b>Payoff date</b></td>';
                        Curr = '<td>' + DateChartRef + '</td>';
                        Ref = '<td>' + DateChart + '</td>';
                        Diff = '<td>' + TermDiff2 + TermDiff2b + '</td></tr></table></small>';
                        break;
                }

                outTableResult.push([Title, Curr, Ref, Diff,].join(''));

            }

            if (hide !== 1) {
                ctx.addHtml(outTableResult.join(''), {afterVariable: 'VarRep'});
            }

            ctx.addHtml('<small>* Figures refer to the post-deferment period.</small>', {afterVariable: 'VarRep'});

            if (VarRep === 1) {
                ctx.hideVariables('TableFreq');
            } else {
                ctx.showVariables('TableFreq');
            }

        } //hide 1
    } //hide2

    if (hide === 1 || hide2 === 1) {
        ctx.hideVariables('represent')
    }

};
omni.onResult(
    ['r', 't', 'm', 'q', 'LoanAmount', 'VarTable', 'VarRep', 'first_day', 'DefFrom', 'DefTill', 'VarInt', 'VarDef', 'Payment', 'VarMode', 'DefPer'],

    callBackOmniOnResultIDKWhatItDoesButItSeemsLikeMainFunction);

///FUNCTIONS

let constructorForOmniDefine = function (_LoanAmount, _q, _t, _m, _r) {

    var LoanAmount = _LoanAmount.toNumber();
    var q = _q.toNumber();
    var t = _t.toNumber();
    var m = _m.toNumber();
    var r = _r.toNumber();

    var ex = Math.exp(1);

    var periods = q * t;

    var eq;
    var eq_p;
    var Pmt;

    var base;
    if (m === 0) {
        var continuous_r = mathjs.pow(ex, r) - 1;
        m = 1;
        base = 1 + continuous_r / m;
    } else {
        base = 1 + r / m;
    }
    var power = m / q;

    eq = q * (mathjs.pow(base, power) - 1);

    eq_p = eq / q;

    Pmt = (LoanAmount * eq_p * mathjs.pow(1 + eq_p, periods)) /
        (mathjs.pow(1 + eq_p, periods) - 1);

    return mathjs.bignumber(mathjs.round(Pmt, 0))

};
omni.define('returnPmt', constructorForOmniDefine);

let anotherConstructorForOmniDefice = function (_LoanAmount, _q, _Payment, _m, _r) {

    var LoanAmount = _LoanAmount.toNumber();
    var q = _q.toNumber();
    var Payment = _Payment.toNumber();
    var m = _m.toNumber();
    var r = _r.toNumber();

    var ex = Math.exp(1);

    var eq;
    var eq_p;
    var Per;

    var base;
    if (m === 0) {
        var continuous_r = mathjs.pow(ex, r) - 1;
        m = 1;
        base = 1 + continuous_r / m;
    } else {
        base = 1 + r / m;
    }
    var power = m / q;

    eq = q * (mathjs.pow(base, power) - 1);

    eq_p = eq / q;

    Per = mathjs.ceil(mathjs.log((Payment) /

        (eq_p * -LoanAmount + Payment)) / mathjs.log(eq_p + 1));

    return mathjs.bignumber(mathjs.round(Per, 0))

};
omni.define('returnPer', anotherConstructorForOmniDefice);

// Hide-show arrangements

let callBackForOmniOnResult = function (ctx, _VarTable) {

    ctx.showVariables('amortization_year');

    var VarTable = _VarTable.toNumber();

    if (VarTable === 2) {

        ctx.hideVariables('amortization_year');
    }

};
omni.onResult(
    ['VarTable'],
    callBackForOmniOnResult
);

omni.onResult(
    function (ctx) {
        ctx.hideVariables('date_extra', 'm', 'q', 'ct2', 'type', 'VarRep', 'VarTable', 'VarInt', 'date_single', 'VarDef', 'VarInt', 'DefInt', 'VarMode', 'Fee', 'DefTill');

    });

function getNewPer(LoanAmount, eq_p, periods, DefInt, pmt) {

    // Algorithm for computing periods of extended term after deferment with distribution of interest accumulation during deferral thorugh the whole loan term - interest is not capitalized

    var iteration = 0;
    var MAX_ITERATIONS = 5000000;
    var MIN_PRECISION = 0.0000000001; //1.0e-8

    var oldPer;
    var NewPer = periods;

    LoanAmount = LoanAmount / 10000
    DefInt = DefInt / 10000
    pmt = pmt / 10000

    do {

        oldPer = NewPer;

        var dividend = (LoanAmount * eq_p * mathjs.pow(1 + eq_p, NewPer)) /
            (mathjs.pow(1 + eq_p, NewPer) - 1) + DefInt / NewPer - pmt;

        var divisor = -mathjs.pow(DefInt / NewPer, 2) -
            (LoanAmount * eq_p * mathjs.pow(1 + eq_p, NewPer) * mathjs.log(eq_p + 1)) /
            (mathjs.pow((mathjs.pow(1 + eq_p, NewPer) - 1), 2));

        NewPer = NewPer - dividend / divisor;

        ++iteration;

    } while (

        Math.abs(oldPer - NewPer) > MIN_PRECISION &&

        iteration <= MAX_ITERATIONS

        );

    NewPer = mathjs.ceil(NewPer)

    //NewPer = mathjs.round(NewPer, 4)

    return NewPer

}

function getDateGapEnd(FirstDay, DateFrom, periodsGap) {

    var i
    var date
    var DateEnd
    var daysInPeriod
    var milisecondsInADay = 24 * 60 * 60 * 1000

    var daysPassed = 0

    // correction for end date when the gat is less than 2 whole months - in this case the end date must be rounded up

    var MonthCorr = 1 // default correcion is 1 for numerical order (0 is January)

    var date2 = new Date(FirstDay * milisecondsInADay)
    var date1 = new Date(DateFrom * milisecondsInADay)

    var DayFrom = date2.getDate()
    var DayTill = date1.getDate()

    if (DayFrom < DayTill) {
        MonthCorr = 2
    }

    for (i = 0; i <= periodsGap + 10; i++) {

        date = new Date((DateFrom + daysPassed) * milisecondsInADay)

        daysInPeriod = getDaysInPeriod(DateFrom, date, 12)

        var Year = date.getFullYear()

        var Month = date.getMonth() + MonthCorr
        var Day = date2.getDate()
        if (Month < 10) {
            Month = '0' + Month;
        }
        if (Day < 10) {
            Day = '0' + Day;
        }

        var dateFormat = Year + '' + Month + '' + Day

        if (i > 0) {

            daysPassed += daysInPeriod

            if (i === periodsGap) {
                DateEnd = dateFormat
            }

        }
    }

    return DateEnd

}

function getDateFormat(date) {

    var dd = date.getDate()
    var mm = date.getMonth() + 1
    var yyyy = date.getFullYear()
    var yy = yyyy.toString().substr(-2)
    var mmNum

    var mm2

    if (mm === 1) {
        mm2 = 'Jan.'
    }
    if (mm === 2) {
        mm2 = 'Feb.'
    }
    if (mm === 3) {
        mm2 = 'Mar.'
    }
    if (mm === 4) {
        mm2 = 'Apr.'
    }
    if (mm === 5) {
        mm2 = 'May.'
    }
    if (mm === 6) {
        mm2 = 'Jun.'
    }
    if (mm === 7) {
        mm2 = 'Jul.'
    }
    if (mm === 8) {
        mm2 = 'Aug.'
    }
    if (mm === 9) {
        mm2 = 'Sept.'
    }
    if (mm === 10) {
        mm2 = 'Oct.'
    }
    if (mm === 11) {
        mm2 = 'Nov.'
    }
    if (mm === 12) {
        mm2 = 'Dec.'
    }

    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }

    if (mm < 10) {
        mmNum = '0' + mm
    } else {
        mmNum = mm
    }

    var dateFull = dd + '.' + mm + '.' + yyyy
    var dateNumFull = mmNum + '.' + dd + '.' + yyyy
    var dateNumShort = mmNum + '.' + dd + '.' + yy
    var dateNumMonth = mmNum + '.' + yyyy
    var dateText = mm + ' ' + dd + ', ' + yyyy

    return dateFull

}

function getDaysInMonth(date) {

    var month = date.getMonth()
    var year = date.getFullYear()
    var daysInMonth

    if (month === 0) {
        daysInMonth = 31
    } else if (month === 1) {
        daysInMonth = 28
    } else if (month === 2) {
        daysInMonth = 31
    } else if (month === 3) {
        daysInMonth = 30
    } else if (month === 4) {
        daysInMonth = 31
    } else if (month === 5) {
        daysInMonth = 30
    } else if (month === 6) {
        daysInMonth = 31
    } else if (month === 7) {
        daysInMonth = 31
    } else if (month === 8) {
        daysInMonth = 30
    } else if (month === 9) {
        daysInMonth = 31
    } else if (month === 10) {
        daysInMonth = 30
    } else if (month === 11) {
        daysInMonth = 31
    }

    if ((year % 4 === 0 && year % 100 !== 0 && month === 1) ||
        (year % 400 === 0 && month === 1)) {
        daysInMonth = 29
    }

    return daysInMonth

}

function getGapPeriods(FirstDay, DateFrom, DateTill) {

    var milisecondsInADay = 24 * 60 * 60 * 1000

    var date1 = new Date(DateFrom * milisecondsInADay)
    var date2 = new Date(DateTill * milisecondsInADay)

    var DayFirst = date1.getDate()
    var DayLast = date2.getDate()

    var DaysInFirstMonth = getDaysInMonth(date1)
    var DaysInLastMonth = getDaysInMonth(date2)

    var DateDue = formatDate(FirstDay)
    var DayDue = parseInt(DateDue.substring(6, 8))

    var DateFrom2 = formatDate(DateFrom)
    var DateTill2 = formatDate(DateTill)
    var YearFrom = parseInt(DateFrom2.substring(0, 4))
    var YearTill = parseInt(DateTill2.substring(0, 4))
    var MonthFrom = parseInt(DateFrom2.substring(4, 6))
    var MonthTill = parseInt(DateTill2.substring(4, 6))
    var DayFrom = parseInt(DateFrom2.substring(6, 8))
    var DayTill = parseInt(DateTill2.substring(6, 8))
    var MonthCorr1 = 0
    var MonthCorr2 = 0
    var MonthCorr3 = 0
    var Gap = 0

    if (DayDue - DayFrom >= 0) {
        MonthCorr1 = 1
    }
    if (DayDue - DayTill > 0) {
        MonthCorr2 = -1
    }

    if (DaysInFirstMonth === DayFirst && DaysInLastMonth === DayLast && DaysInFirstMonth !== DaysInLastMonth) {
        MonthCorr3 = 1
    } //correction if the first day and the last day is the last day of the month i.e. first day is 31/08 last day 30/09 => its already one period

    Gap = (((YearTill - YearFrom) * 12) + (MonthTill - MonthFrom) + MonthCorr1 + MonthCorr2 + MonthCorr3)

    return Gap

}

function formatTerm(periods) {

    var year = parseInt((Math.trunc(periods / 12)));
    var month = parseInt(Math.ceil(periods - (year * 12)));

    var Term = 0;

    if ((year === 1) && (month === 0)) {
        Term = (year + ' year');
    }
    if ((year > 1) && (month === 0)) {
        Term = (year + ' years');
    }
    if ((year === 0) && (month === 1)) {
        Term = (month + ' month');
    }
    if ((year === 0) && (month > 1)) {
        Term = (month + ' months');
    }
    if ((year === 1) && (month === 1)) {
        Term = (year + ' year and ' + month + ' month');
    }
    if ((year > 1) && (month === 1)) {
        Term = (year + ' years and ' + month + ' month');
    }
    if ((year === 1) && (month > 1)) {
        Term = (year + ' year and ' + month + ' months');
    }
    if ((year > 1) && (month > 1)) {
        Term = (year + ' years and ' + month + ' months');
    }
    return Term

}

function formatDate(date) {

    date = new Date(date * 24 * 60 * 60 * 1000)

    var Year = date.getFullYear()
    var Month = date.getMonth() + 1
    var Day = date.getDate()
    if (Month < 10) {
        Month = '0' + Month;
    }
    if (Day < 10) {
        Day = '0' + Day;
    }

    return Year + '' + Month + '' + Day

}

//** Calculating periodic payment based on term (according to the payment frequency - q)
function getPmt(LoanAmount, periods, eq_p) {

    return (LoanAmount * eq_p * mathjs.pow(1 + eq_p, periods)) /
        (mathjs.pow(1 + eq_p, periods) - 1)

}

function getPer(LoanAmount, Pmt, eq_p) {

    // LoanAmount: loan balance
    // eq_p: Periodic Equivalent Rate
    // Pmt: Payment

    var Per;

    Per = mathjs.ceil(mathjs.log((Pmt) /
        (eq_p * -LoanAmount + Pmt)) / mathjs.log(eq_p + 1));

    if (Per > 0) {
        Per = Per
    } else {
        Per = 0
    } //returns 0 if NaN

    return Per

}

//** Periodic equivalent rate - eq_p (according to the payment frequency - q)
function getPerEqRate(paymentFrequency, compoundFrequency, annualInterestRate) {

    // q: payment frequency
    // m: compound frequency
    // r: annual interest rate

    var ex = Math.exp(1)

    var eq_p

    var base;
    if (compoundFrequency === 0) {
        var continuous_r = mathjs.pow(ex, annualInterestRate) - 1
        compoundFrequency = 1
        base = 1 + continuous_r / compoundFrequency
    } else {
        base = 1 + annualInterestRate / compoundFrequency
    }
    var power = compoundFrequency / paymentFrequency

    eq_p = mathjs.round(mathjs.pow(base, power) - 1, 10)

    return eq_p

}

function getDaysInPeriod(FirstDay, date, q) {

    var milisecondsInADay = 24 * 60 * 60 * 1000

    FirstDay = new Date(FirstDay * milisecondsInADay)

    var daysInMonth
    var daysInPeriod

    var DaysInYear
    var DaysInNextYear

    var day = FirstDay.getDate()
    var month = date.getMonth()
    var year = date.getFullYear()

    if (day < 29 && month === 0) {
        daysInMonth = 31
    } else if (day === 29 && month === 0) {
        daysInMonth = 30
    } else if (day === 30 && month === 0) {
        daysInMonth = 29
    } else if (day === 31 && month === 0) {
        daysInMonth = 28
    } else if (day < 29 && month === 1) {
        daysInMonth = 28
    } else if (day === 29 && month === 1) {
        daysInMonth = 29
    } else if (day === 30 && month === 1) {
        daysInMonth = 30
    } else if (day === 31 && month === 1) {
        daysInMonth = 31
    } else if (day < 31 && month === 2) {
        daysInMonth = 31
    } else if (day === 31 && month === 2) {
        daysInMonth = 30
    } else if (day < 31 && month === 3) {
        daysInMonth = 30
    } else if (day === 31 && month === 3) {
        daysInMonth = 31
    } else if (day < 31 && month === 4) {
        daysInMonth = 31
    } else if (day === 31 && month === 4) {
        daysInMonth = 30
    } else if (day < 31 && month === 5) {
        daysInMonth = 30
    } else if (day === 31 && month === 5) {
        daysInMonth = 31
    } else if (day < 31 && month === 6) {
        daysInMonth = 31
    } else if (day === 31 && month === 6) {
        daysInMonth = 31
    } else if (day < 31 && month === 7) {
        daysInMonth = 31
    } else if (day === 31 && month === 7) {
        daysInMonth = 30
    } else if (day < 31 && month === 8) {
        daysInMonth = 30
    } else if (day === 31 && month === 8) {
        daysInMonth = 31
    } else if (day < 31 && month === 9) {
        daysInMonth = 31
    } else if (day === 31 && month === 9) {
        daysInMonth = 30
    } else if (day < 31 && month === 10) {
        daysInMonth = 30
    } else if (day === 31 && month === 10) {
        daysInMonth = 31
    } else if (day < 31 && month === 11) {
        daysInMonth = 31
    } else if (day === 31 && month === 11) {
        daysInMonth = 31
    }

    if ((year % 4 === 0 && // divisible by 4
            year % 100 !== 0 && // not divisble by 100
            month === 1) ||  // february
        (year % 400 === 0 &&  // div by 400
            month === 1)) {
        daysInMonth = 29
    } // leap year adjustment
    if ((year % 4 === 0 && // divisible by 4
            year % 100 !== 0 && // not divisble by 100
            month === 0 && date.getDate() === 30) ||
        (year % 400 === 0 &&  // div by 400
            month === 0 && date.getDate() === 30)) {
        daysInMonth = 30
    }
    if ((year % 4 === 0 && // divisible by 4
            year % 100 !== 0 && // not divisble by 100
            month === 0 && date.getDate() === 31) ||
        (year % 400 === 0 &&  // div by 400
            month === 0 && date.getDate() === 31)) {
        daysInMonth = 29
    }
    if ((year % 4 === 0 && // divisible by 4
            year % 100 !== 0 && // not divisble by 100
            month === 0 && date.getDate() === 29) ||
        (year % 400 === 0 &&  // div by 400
            month === 0 && date.getDate() === 29)) {
        daysInMonth = 31
    }

    if ((date.getDate() === 29) && (date.getMonth() === 1) &&
        (day > 29)) {
        daysInMonth = 30
    } // adjustment for march
    if ((date.getDate() === 29) && (date.getMonth() === 1) &&
        (day > 30)) {
        daysInMonth = 31
    } // adjustment for march
    if ((date.getDate() === 28) && (date.getMonth() === 1) &&
        (day > 30)) {
        daysInMonth = 31
    } // adjustment for march

    if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) {
        DaysInYear = 366
    } else {
        DaysInYear = 365
    }
    if (((year + 1) % 4 === 0 && (year + 1) % 100 !== 0) || (year + 1) % 400 === 0) {
        DaysInNextYear = 366
    } else {
        DaysInNextYear = 365
    }

    if (q === 1) {
        daysInPeriod = DaysInYear
    }
    if (q === 12) {
        daysInPeriod = daysInMonth
    }
    if (q === 26) {
        daysInPeriod = 14
    }
    if (q === 52) {
        daysInPeriod = 7
    }
    if (q === 365) {
        daysInPeriod = 1
    }

    if (q === 4) {

        day = date.getDate()

        if (month === 0 && DaysInYear === 366) {
            daysInPeriod = 91
        }
        if (month === 0 && DaysInYear === 365) {
            daysInPeriod = 90
        }
        if (month === 1 && DaysInYear === 366) {
            daysInPeriod = 90
        }
        if (month === 1 && DaysInYear === 365) {
            daysInPeriod = 89
        }
        if (month === 11 && DaysInNextYear === 366) {
            daysInPeriod = 92
        }
        if (month === 11 && DaysInNextYear === 365) {
            daysInPeriod = 91
        }
        if (month === 2 || month === 4 || month === 6 || month === 7 || month === 9 || month === 10) {
            daysInPeriod = 92
        }
        if (month === 3 || month === 5 || month === 8) {
            daysInPeriod = 91
        }

        if (day === 31 && (month === 2 || month === 7)) {
            daysInPeriod = 91
        }
        if (day === 30 && (month === 3 || month === 8 || month === 5)) {
            daysInPeriod = 92
        }

        if (day === 31 && month === 0 && DaysInYear === 365) {
            daysInPeriod = 89
        }
        if (day === 31 && month === 0 && DaysInYear === 366) {
            daysInPeriod = 90
        }

        if (day === 31 && (month === 4 || month === 9)) {
            daysInPeriod = 92
        }

        if (day >= 30 && month >= 10 && DaysInNextYear === 366) {
            daysInPeriod = 91
        }
        if (day >= 30 && month >= 10 && DaysInNextYear === 365) {
            daysInPeriod = 90
        }

        if (day === 28 && month === 1 && DaysInYear === 365) {
            daysInPeriod = 92
        }
        if (day === 28 && month === 1 && DaysInYear === 366) {
            daysInPeriod = 93
        }
        if (day === 29 && month === 1) {
            daysInPeriod = 92
        }

    }

    if (q === 2) {
        daysInPeriod = 180
    }

    return daysInPeriod

}

function formatCurrency(number) {

    if (number === undefined) {
        return 'undefined';
    }

    var currencyCode, userLang;
    var scientific_threshold = 1000000000; //after which value the currency should be formatted in scientific notation?

    if (mathjs.abs(number) < scientific_threshold && !Math.round(number, 2).toString().includes('e+')) {
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
        if (!Math.round(number, 2).toString().includes('e+')) {
            scientific_notation = number.toExponential().split('e+');
        } else {
            scientific_notation = Math.round(number, 2).toString().split('e+');
        }

        var minus = '';

        if (scientific_notation[0].includes('-')) {
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

            if (is_currency_before) {
                return minus + new_currencySymbol + scientific_notation[0].toLocaleString(userLang) + '*10' + sup(scientific_notation[1]);
            } else {
                return minus + scientific_notation[0].toLocaleString(userLang) + '*10' + sup(scientific_notation[1]) + new_currencySymbol;
            }
        }
    }

    //extra function for superscript generation
    function sup(number) {
        if (number === undefined) {
            return 'ᴺᵃᴺ';
        }
        return number.toString().replace(/./g, superscriptFromDigit);

        function superscriptFromDigit(singleNumber) {
            var char = "⁰¹²³⁴⁵⁶⁷⁸⁹".charAt(singleNumber);
            if (char !== '') {
                return char;
            } else {
                return singleNumber;
            }
        }
    }
}

function numberWithCommas(x) {

    if (country !== 'IN') {

        return x = x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')

    } else {

        x = mathjs.round(x, 0)
        x = x.toString()
        var lastThree = x.substring(x.length - 3)
        var otherNumbers = x.substring(0, x.length - 3)
        if (otherNumbers !== '') {
            lastThree = ',' + lastThree
        }

        return x = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree

    }
}

var manualFormatBefore = [
    'BH', 'BJ', 'BT', 'CN', 'EG', 'ET', 'GH', 'GN', 'IS', 'IQ', 'IL', 'JO', 'KW', 'LB', 'MV', 'MN', 'MZ',
    'MM', 'NA', 'NP', 'OM', 'PH', 'QA', 'SA', 'SD', 'CH', 'SY', 'TG', 'TM', 'UG', 'UY', 'UZ', 'YE',
    'ZW', 'IN'
];

var manualFormatAfter = [
    'AF', 'BD', 'BY', 'BF', 'TD', 'KM', 'DJ', 'ER', 'GW', 'IR', 'ML', 'MR', 'NE', 'RO', 'ST', 'SN',
    'RS', 'SO', 'UA', 'AE'
];

var autoFormatCountries = {
    RU: ['ru-RU', 'RUB']
};
//# sourceURL=https://www.omnicalculator.com/customjs/deferred-payment-loan.js