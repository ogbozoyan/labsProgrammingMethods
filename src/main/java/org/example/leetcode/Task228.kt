package org.example.leetcode

/*
You are given a sorted unique integer array nums.

A range [a,b] is the set of all integers from a to b (inclusive).

Return the smallest sorted list of ranges that cover all the numbers in the array exactly. That is, each element of nums is covered by exactly one of the ranges, and there is no integer x such that x is in one of the ranges but not in nums.

Each range [a,b] in the list should be output as:

"a->b" if a != b
"a" if a == b


Example 1:

Input: nums = [0,1,2,4,5,7]
Output: ["0->2","4->5","7"]
Explanation: The ranges are:
[0,2] --> "0->2"
[4,5] --> "4->5"
[7,7] --> "7"
Example 2:

Input: nums = [0,2,3,4,6,8,9]
Output: ["0","2->4","6","8->9"]
Explanation: The ranges are:
[0,0] --> "0"
[2,4] --> "2->4"
[6,6] --> "6"
[8,9] --> "8->9"


Constraints:

0 <= nums.length <= 20
-231 <= nums[i] <= 231 - 1
All the values of nums are unique.
nums is sorted in ascending order.
 */
fun main(args: Array<String>) {
    class Solution {
        fun summaryRanges(nums: IntArray): List<String> {
            val res = ArrayList<String>()

            if (nums.isEmpty()) {
                return res
            } else if (nums.size == 1) {
                res.add(nums[0].toString())
                return res
            } else if (nums.size == 2) {
                if (nums[1] == nums[0] + 1) {
                    res.add(nums[0].toString() + "->" + nums[1].toString())
                } else if (nums[1] == nums[0]) {
                    res.add(nums[1].toString())
                } else {
                    res.add(nums[0].toString())
                    res.add(nums[1].toString())
                }
                return res
            }

            var startRange = 0;
            var endRange = 0;

            for (i in 1 until nums.size) {

                val cur = nums[i]
                val prev = nums[i - 1]

                if (cur == prev + 1) {
                    endRange = i;
                } else {

                    pushToRes(startRange, endRange, res, nums)
                    startRange = i
                    endRange = i
                }
            }

            pushToRes(startRange, endRange, res, nums)
            return res;
        }

        private fun pushToRes(startRange: Int, endRange: Int, res: ArrayList<String>, nums: IntArray) {
            if (startRange != endRange) {
                res.add(nums[startRange].toString() + "->" + nums[endRange].toString())
            } else {
                res.add(nums[startRange].toString())
            }
        }
    }

    val solution = Solution()
    val message = solution.summaryRanges(intArrayOf(-1, 1, 2))
    println(message)
}