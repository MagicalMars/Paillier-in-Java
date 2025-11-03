import java.math.BigInteger;
import java.util.Scanner;
public class Paillier{//I used to think it was spelt as Pallier.
	public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("-----It's a Paillier calc. Calc is short for calculator-----"); //Obscure calc is short for calculator reference. It's not very funny but this is *MY* code, I can be cringe.
        System.out.println("Please input message (m1)");
		BigInteger m = scnr.nextBigInteger();
		System.out.println("Please input p");
		BigInteger p = scnr.nextBigInteger();
		System.out.println("Please input q");
		BigInteger q = scnr.nextBigInteger();
		System.out.println("Please input g");
		BigInteger g = scnr.nextBigInteger();
		System.out.println("Please input r");
		BigInteger r = scnr.nextBigInteger();

        //n = p*q
        BigInteger n = p.multiply(q);

        //pubkey is (n, g)

        //n squared is n squared (suprising)
        BigInteger nSq = n.multiply(n);

        //sender equation
        //c = g^m * r^n mod n^2
        BigInteger c = g.modPow(m, nSq).multiply(r.modPow(n, nSq)).mod(nSq);
		//I do not like this equation

        //receiver equations

        //lambda = lcm of p-1 and q-1. Scary
        BigInteger lambda = BigInteger.ZERO; //Initialising lambda because Java is silly.
        //made up, fully original, likely not size efficient lcm finder. I am proud of it. 
        //It subtracts one from initial values meaning that that part must be removed to make it a normal lcm finder
        boolean match = false;
        BigInteger i = BigInteger.ONE;
        BigInteger j = BigInteger.ONE;
        while (match == false) {
            if (p.subtract(BigInteger.ONE).multiply(i).compareTo(q.subtract(BigInteger.ONE).multiply(j)) == 1){//terrifying if condition
                //p*i is greater
                j = j.add(BigInteger.ONE);
            } else if (p.subtract(BigInteger.ONE).multiply(i).compareTo(q.subtract(BigInteger.ONE).multiply(j)) == -1){
                //q*j is greater
                i = i.add(BigInteger.ONE);
            } else {
                match = true;
                lambda = p.subtract(BigInteger.ONE).multiply(i);
            }
        }
        System.out.println(lambda);
        //k = L(g^lambda mod n^2) = (g^lambda mod n^2 -1) /n
        BigInteger k = g.modPow(lambda, nSq).subtract(BigInteger.ONE).divide(n);
        //mu = k^-1 mod n
        BigInteger mu = k.modInverse(n);

        //privkey is (lambda, mu)

        //m2 = L(x^lambda mod n^2) * mu mod n
        BigInteger m2 = c.modPow(lambda, nSq).subtract(BigInteger.ONE).divide(n).multiply(mu).mod(n);


        //printing formulas
        System.out.println("n = p * q");
        System.out.println(n + " = " + p + " * " + q);

        System.out.println("\nPublic key (n, g):");
        System.out.println("n = " + n);
        System.out.println("g = " + g);

        System.out.println("\nEncryption");
        System.out.println("c = g^m * r^n mod n^2");
        System.out.println(c + " = " + g + "^" + m + " * " + r + "^" + n + " mod " + n + "^2");

        System.out.println("\nlambda = lcm(p-1,q-1)");
        System.out.println(lambda + " = lcm(" + p + "-1, " + q +"-1)");

        System.out.println("\nk = L(g^lambda mod n^2)");
        System.out.println(k + " = L(" + g + "^" + lambda + " mod " + n + "^2)");

        System.out.println("\nmu = k^-1 mod n");
        System.out.println(mu + " = " + k + "^-1 mod " + n);

        System.out.println("\nPrivate key (lambda, mu):");
        System.out.println("lambda = " + lambda);
        System.out.println("mu = " + mu);

        System.out.println("\nDecryption");
        System.out.println("m2 = L(c^lambda mod n^2) * mu mod n");
        System.out.println(m2 + " = L(" + c + "^" + lambda + " mod " + n + "^2) * " + mu + " mod " + n);

        //printing variables
        System.out.println("\nAll values:");
        System.out.println("m1:\t" + m);
        System.out.println("p:\t" + p);
        System.out.println("g:\t" + g);
        System.out.println("r:\t" + r);
        System.out.println("n:\t" + n);
        System.out.println("c:\t" + c);
        System.out.println("lambda:\t" + lambda);
        System.out.println("k:\t" + k);
        System.out.println("mu:\t" + mu);
        System.out.println("m2:\t" + m2);

        if(m.compareTo(m2) == 0) {
			System.out.println("m1 (initial message) and m2 (decrypted message) match\n");
		} else {
			System.out.println("Invalid inputs. Please try again\n");//hopefully not my fault
		}
		scnr.close();

    }

}