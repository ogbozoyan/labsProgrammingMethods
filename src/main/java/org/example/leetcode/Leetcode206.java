package org.example.leetcode;

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

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +

                    '}';
        }
    }

    /*

             prev cur next
              |    |    |
                   1 -> 2 -> 3 -> 4 -> 5

                    prev = cur
                    cur.next = prev
                    cur = next
                    next = next.next

                   prev cur next
                   |    |    |
                   1 <- 2 -> 3 -> 4 -> 5

                       prev cur next
                       |    |    |
                   1 -> 2 -> 3 -> 4 -> 5


                               prev cur next
                               |    |    |
                   1 -> 2  ->  3 -> 4 -> 5

                                    prev cur next
                                    |    |    |
                   1 -> 2  ->  3 -> 4 -> 5
                */

    static class Solution {
        public ListNode reverseList(ListNode head) {

            if(head == null || head.next == null){
                return head;
            }

            ListNode prev = null;
            ListNode cur = head;
            ListNode next = cur.next;

            while (next != null) {
                prev = cur;
                cur.next = prev;
                cur = next;
                next = cur.next;

                if(next == null){
                    cur.next = prev;
                }
            }

            return cur;
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

