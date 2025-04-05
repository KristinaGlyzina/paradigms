package search;

public class BinarySearch {
    // Pre: x >= 0; int[]a != null; For all a[i] >= a[j] for any i < j.
    // Post: (min) i - a[i] <= x.
    public static int iterativeSearch(int x, int[] a) {
        int left = -1;
        // left = -1;
        int right = a.length;
        // right = a.length;

        // Pre: left + 1 < right && left < a[middle] < right;
        while (left + 1 < right) {
            int middle = (left + right) / 2;
            // middle = (left + right) / 2;

            if (a[middle] <= x) {
                // a[middle]' <= x && x' > 0;
                right = middle;
                //I: right' = middle;
            } else {
                // a[middle]' > x && left' = middle;
                left = middle;
                //I: left' = middle;
            }
        }
        return right;
    }

    // :NOTE: x >= 0?
    // Pre: x >= 0; int[]a != null; For all a[i] >= a[j] for any i < j.
    // :NOTE: под предусловие подходит a = [1], left = 1000, right = -1000, x = 10
    // Post: (min) i: a[i] <= x || a.length if a[i] > x forall i.
    // 10 9 8, x = 5 -> 3
    public static int recursiveSearch(int x, int[] a, int left, int right) {
        // left < right && left >= 0 && right <= a.length
        if (left + 1 == right) {
            // a[left] > x >= a[right] && left + 1 == right => a[left + 1] <= x && (min) i: a[i] <= x;
            return right;
        }
        // left < right && left + 1 == right;
        int middle = (left + right) / 2;
        // left < right && middle = (left + right) / 2;
        if (a[middle] <= x) {
            // x' > 0 && middle = (left + right) / 2 && right == mid && a[middle] <= x;
            return recursiveSearch(x, a, left, middle);
        } else {
            // x' > 0 && middle = (left + right) / 2 && left == mid && a[middle] > x;
            return recursiveSearch(x, a, middle, right);
        }
    }

    // :NOTE: контракт для main
    public static void main(String[] args) {
        // args != null;
        int x = Integer.parseInt(args[0]);
        // x = Integer value;
        //For all a[i] >= a[j] for any i < j
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }

        int sum = 0;
        for (int num : a) {
            sum += num;
        }
        if (sum % 2 == 0) {
            // sum % 2 == 0 -> call the iterative search;
            int iterative = iterativeSearch(x, a);
            //iterative a[i] <= x.
            System.out.println(iterative);
        } else {
            // sum % 2 != 0-> call the recursive search;
            int recursive = recursiveSearch(x, a, -1, a.length);
            //recursive a[i] <= x.
            System.out.println(recursive);
        }
    }
}
