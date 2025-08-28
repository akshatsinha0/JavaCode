/***
     // Given to take the even data values from the singly linked list and function would simply return the ll with the nodes which carry the
      // ODD values
      //very familiarly can be soved in the opposite case as well//
  ***/

    public static Node removeEven(Node head) _
        Node dummy = new Node(0);
        dummy.next = head;
        Node cur = dummy;
        while (cur.next != null) {
            if ((cur.next.data & 1) == 0) { // even
                cur.next = cur.next.next;   //even is getting skippped
            } else {
                cur = cur.next;             //and we are keeping the odd value data nodes heree
            }
        }
        return dummy.next;
    }
