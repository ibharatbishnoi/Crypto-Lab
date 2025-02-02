import java.util.HashSet;
class PrimitiveRoot {
    static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i = i + 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    static int power(int x, int y, int p) {
        int res = 1;
        x = x % p;
        while (y > 0) {
            if (y % 2 == 1) {
                res = (res * x) % p;
            }
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }
    static void findPrimefactors(HashSet<Integer> s, int n) {
        while (n % 2 == 0) {
            s.add(2);
            n = n / 2;
        }
        for (int i = 3; i <= Math.sqrt(n); i = i + 2) {
            while (n % i == 0) {
                s.add(i);
                n = n / i;
            }
        }
        if (n > 2) {
            s.add(n);
        }
    }
    public static int findPrimitive(int n) {
        HashSet<Integer> s = new HashSet<>();
        // Check if n is prime or not
        if (isPrime(n) == false) {
            return -1;
        }
        int phi = n - 1;
        findPrimefactors(s, phi);
        for (int r = 2; r <= phi; r++) {
            boolean flag = false;
            for (Integer a : s) {
                if (power(r, phi / (a), n) == 1) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                return r;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int n = 40487;
        System.out.println("Smallest primitive root of " + n + " is " + findPrimitive(n));
    }
}