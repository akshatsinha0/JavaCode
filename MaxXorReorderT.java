/**
Count the number of 1s and 0s in both the strings.
Place zeros of t below the 1s of s:
These cases will be there to form:
1) either all the zeroes of t are used and all the 1s of s are used
->In this case, we can simply place the rest of t 1s below s 0s, and then parse and xor it
2) either all the 1s of s are used up with the zeros of t, but t still has some zeros left
-> In this case, first place the ones of t below the available 0s of s and then place left zeros of t below the left 0s of s, and then parse and xor it
3) the zeros of t cannot complete the 1s  of s
-> In this case, place those many zeros of t below ones of s, then we still have some ones of s left and also zeros, here, put the remaining ones of t below the remainig digits of s in that order only, then xor and find the result.
  **/

// Below is the implemented java code mani-padi the algo thought process:->

import java.math.BigInteger;
import java.util.Objects;
public class MaxXorFollowAlgorithm {
    public static String maxXorBitsByReorderingT(String s, String t) {
        Objects.requireNonNull(s, "s");
        Objects.requireNonNull(t, "t");
        if (s.length() != t.length()) {
            throw new IllegalArgumentException("s and t must have equal length");
        }
        final int n = s.length();
        int onesS = 0, zerosS = 0, onesT = 0, zerosT = 0;
        for (int i = 0; i < n; i++) {
            char cs = s.charAt(i);
            if (cs == '1') onesS++;
            else if (cs == '0') zerosS++;
            else throw new IllegalArgumentException("s must be a binary string");
            char ct = t.charAt(i);
            if (ct == '1') onesT++;
            else if (ct == '0') zerosT++;
            else throw new IllegalArgumentException("t must be a binary string");
        }
        char[] arranged = new char[n]; // '\0' means unfilled
        int zerosTLeft = zerosT;
        int onesTLeft = onesT;
        for (int i = 0; i < n && zerosTLeft > 0; i++) {
            if (s.charAt(i) == '1') {
                arranged[i] = '0';
                zerosTLeft--;
            }
        }
        if (zerosT == onesS) {
            for (int i = 0; i < n; i++) {
                if (arranged[i] == '\0') {
                    arranged[i] = '1';
                    onesTLeft--;
                }
            }
        } else if (zerosT > onesS) {
            for (int i = 0; i < n && onesTLeft > 0; i++) {
                if (arranged[i] == '\0' && s.charAt(i) == '0') {
                    arranged[i] = '1';
                    onesTLeft--;
                }
            }
            for (int i = 0; i < n && zerosTLeft > 0; i++) {
                if (arranged[i] == '\0' && s.charAt(i) == '0') {
                    arranged[i] = '0';
                    zerosTLeft--;
                }
            }
            for (int i = 0; i < n; i++) {
                if (arranged[i] == '\0') {
                    if (onesTLeft > 0) { arranged[i] = '1'; onesTLeft--; }
                    else if (zerosTLeft > 0) { arranged[i] = '0'; zerosTLeft--; }
                }
            }
        } else {
            for (int i = 0; i < n && onesTLeft > 0; i++) {
                if (arranged[i] == '\0') {
                    arranged[i] = '1';
                    onesTLeft--;
                }
            }
            for (int i = 0; i < n; i++) {
                if (arranged[i] == '\0') {
                    arranged[i] = '0';
                }
            }
        }
        StringBuilder xor = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            char xb = (char) (((s.charAt(i) - '0') ^ (arranged[i] - '0')) + '0');
            xor.append(xb);
        }
        return xor.toString();
    }
    public static BigInteger toDecimal(String bits) {
        return bits.isEmpty() ? BigInteger.ZERO : new BigInteger(bits, 2);
    }
    public static String arrangedT(String s, String t) {
        int n = s.length();
        int onesS = 0, zerosT = 0, onesT = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') onesS++;
            if (t.charAt(i) == '1') onesT++; else if (t.charAt(i) == '0') zerosT++;
        }
        char[] arranged = new char[n];
        int zerosTLeft = zerosT, onesTLeft = onesT;
        for (int i = 0; i < n && zerosTLeft > 0; i++) {
            if (s.charAt(i) == '1') {
                arranged[i] = '0';
                zerosTLeft--;
            }
        }
        if (zerosT == onesS) {
            for (int i = 0; i < n; i++) {
                if (arranged[i] == '\0') {
                    arranged[i] = '1';
                    onesTLeft--;
                }
            }
        } else if (zerosT > onesS) {
            for (int i = 0; i < n && onesTLeft > 0; i++) {
                if (arranged[i] == '\0' && s.charAt(i) == '0') {
                    arranged[i] = '1';
                    onesTLeft--;
                }
            }
            for (int i = 0; i < n && zerosTLeft > 0; i++) {
                if (arranged[i] == '\0' && s.charAt(i) == '0') {
                    arranged[i] = '0';
                    zerosTLeft--;
                }
            }
            for (int i = 0; i < n; i++) {
                if (arranged[i] == '\0') {
                    if (onesTLeft > 0) { arranged[i] = '1'; onesTLeft--; }
                    else if (zerosTLeft > 0) { arranged[i] = '0'; zerosTLeft--; }
                }
            }
        } else {
            for (int i = 0; i < n && onesTLeft > 0; i++) {
                if (arranged[i] == '\0') {
                    arranged[i] = '1';
                    onesTLeft--;
                }
            }
            for (int i = 0; i < n; i++) {
                if (arranged[i] == '\0') {
                    arranged[i] = '0';
                }
            }
        }
        return new String(arranged);
    }
    public static void main(String[] args) {
        String s = "101001";
        String t = "010011";
        String xorBits = maxXorBitsByReorderingT(s, t);
        System.out.println("XOR bits:    " + xorBits);
        System.out.println("XOR decimal: " + toDecimal(xorBits));
        System.out.println("Arranged t:  " + arrangedT(s, t));
    }
}
