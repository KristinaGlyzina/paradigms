package search;

public class BinarySearchClosestD {
    // Pre: x >= 0; int[]a != null; For all a[i] >= a[j] for any i < j.
    // Post: result: i: a[i] <= x.
    public static int iterativeSearch(int x, int[] a) {
        // x>= a[0];
        if (x >= a[0]) {
            // x>= a[0] && a[0]' == a[0];
            return a[0];
        }

        // x <= a[a.length - 1];
        if (x <= a[a.length - 1]) {
            // x <= a[a.length - 1] && a[a.length - 1]' == a[a.length - 1];
            return a[a.length - 1];
        }

        int left = 0;
        // left = 0;
        int right = a.length - 1;
        // right = a.length - 1;

        // left <= right && a[left] <= x <= a[right];
        while (left <= right) {
            // left <= right && middle = (left + right) / 2;
            int middle = (left + right) / 2;

            if (a[middle] == x) {
                return a[middle];
                // a[middle] == x && result: i: a[i] <= x;
            } else if (a[middle] < x) {
                right = middle - 1;
                // a[middle] < x && right = middle - 1;
            } else {
                // a[middle] >= x && left = middle + 1;
                left = middle + 1;
            }
        }

        if (Math.abs(a[left] - x) < Math.abs(x - a[right])) {
            // |a[left] - x| < |x - a[right]| -> a[i] <= x;
            return a[left];
        } else {
            // |a[left] - x| >= |x - a[right]| -> a[i] <= x;
            return a[right];
        }
    }

    // :NOTE: l <= r
    // Pre: x >= 0; int[]a != null; For all a[i] >= a[j] for any i < j.
    // :NOTE: постусловие
    // Post: Res = argmin |a[i] - x|.
    public static int recursiveSearch(int x, int[] a, int left, int right) {
        if (x >= a[0]) {
            // x >= a[0] -> a[i] <= x;
            return a[0];
        }

        if (x <= a[a.length - 1]) {
            // x <= a[a.length - 1] -> a[i] <= x;
            return a[a.length - 1];
        }
        if (left + 1 == right) {
            // left + 1 == right && left < right;
            if (Math.abs(a[left] - x) < Math.abs(x - a[right])) {
                // left + 1 == right && left < right && |a[left] - x| < |x - a[right]| -> a[i] <= x;
                return a[left];
            } else if (Math.abs(a[left] - x) == Math.abs(x - a[right])) {
                // left + 1 == right && left < right && |a[left] - x| == |x - a[right]| -> a[i] <= x;
                return a[left];
            } else {
                // left + 1 == right && left < right && |a[left] - x| > |x - a[right]| -> a[i] <= x;
                return a[right];
            }
        }
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

    public static void main(String[] args) {
        // args != null;
        int x = Integer.parseInt(args[0]);
        // x = Integer value;
        //For all a[i] >= a[j] for any i < j
        int[] array = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            array[i - 1] = Integer.parseInt(args[i]);
        }
        int sum = 0;
        for (int num : array) {
            sum += num;
        }

        if (sum % 2 == 0) {
            // sum % 2 == 0 -> call the iterative search;
            int iterative = iterativeSearch(x, array);
            //iterative a[i] <= x.
            System.out.println(iterative);

        } else {
            // sum % 2 != 0-> call the recursive search;
            int recursive = recursiveSearch(x, array, 0, array.length - 1);
            //recursive a[i] <= x.
            System.out.println(recursive);
        }
    }
}