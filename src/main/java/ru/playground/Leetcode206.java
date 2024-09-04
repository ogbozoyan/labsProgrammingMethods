package ru.playground;

/**
 * @author ogbozoyan
 * @since 03.09.2024
 */
public class Leetcode206 {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    static class Solution {
        public ListNode reverseList(ListNode head) {
            ListNode prev = null;

        }
    }

    public static void main(String[] args) {
        System.out.println(
                new Solution().reverseList(
                        new ListNode(1,
                                new ListNode(2,
                                        new ListNode(3,
                                                new ListNode(4,
                                                        new ListNode(5)
                                                )
                                        )
                                )
                        )
                )
        );
    }
}

