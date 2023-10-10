package org.example;

/**
 * @author ogbozoyan
 * @since 04.09.2023
 */
public class Solution {
    public static int[] twoSum(int[] nums, int target) {
        int[] res = new int[0];
        for (int i = 0; i < nums.length; i++) {
            for (int j = (nums.length - 1); j > i; j--) {
                int sum = nums[i] + nums[j];
                if (sum == target) {
                    res = new int[]{i, j};
                    break;
                }
            }
        }
        return res;
    }

    public static boolean isPalindrome(int x) {
        String origin = String.valueOf(x);
        StringBuilder reversed = new StringBuilder(origin);
        reversed.reverse();
        return origin.contentEquals(reversed);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        return new ListNode();
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode res = new ListNode();
        boolean listEmpty = false;
        boolean skipFirst = false;
        boolean skipSecond = false;
        int iter = 0;
        while (!listEmpty) {
            ListNode buf = res.next;

            if (list1 == null || list1.next == null) {
                skipFirst = true;
            }
            if (list2 == null || list2.next == null) {
                skipSecond = true;
            }
            if (skipFirst && skipSecond) {
                listEmpty = true;
            }

            if (!skipFirst) {
                if (iter == 0) {
                    res = new ListNode(list1.val);
                    list1 = list1.next;
                } else {
                    buf = list1;
                    res.next = buf;
                    list1 = list1.next;
                }
            }
            if (!skipSecond) {
                if (iter == 0) {
                    res = new ListNode(list2.val);
                    list2 = list2.next;
                } else {
                    buf = list2;
                    res.next = buf;
                    list2 = list2.next;
                }
            }
            iter++;
        }
        return res;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        listNode1.setNext(new ListNode(2));
        listNode1.setNext(new ListNode(4));

        ListNode listNode2 = new ListNode(1);
        listNode2.setNext(new ListNode(3));
        listNode2.setNext(new ListNode(4));

        ListNode listNode = mergeTwoLists(listNode1, listNode2);
        System.out.println(listNode);
    }

    static class ListNode {
        private int val;
        private ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }
}
