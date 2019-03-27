package sample;

import java.math.BigInteger;

/******************************************************************************************
 This class allows for RSA encryption. Its constructor must take in two large prime integers
 and a guess for an encryption exponent. If the encryption exponent does not have a greatest
 common factor of one with the product of both large primes minus one (as required by RSA)
 an error will be thrown. Errors are also thrown if the messages that are attempted to be
 encrypted or decrypted with the public methods are longer than the modulus found.

 @author Nathan Wichman
 @version Winter 2019
 *******************************************************************************************/
public class RSA {
    private BigInteger p; //A large prime integer
    private BigInteger q; //Another large prime integer
    private BigInteger m; //Modulus
    private BigInteger e; //encryption exponent
    private BigInteger d; //decryption exponent

    /*Empty constructor, used to have the contents of the setup method,
    but when using in the main method, it had to be in a llambda function
    which did not work unless it was already instantiated. So 'setup' basically
    is the constructor. It must be called before any work can be done with this
    class
     */
    public RSA(){}

    /***********************************************************************************************
     * Basically the constructor. Takes in large integers p, q, and a guess for the encryption
     * exponent e. If e is incorrect, it is divided by the greatest common factor between
     * itself and (p-1)(q-1). m is the modulus which is instantiated to p*q. d is the decryption
     * exponent, which is instantiated to the inverse of e modulo (p-1)(q-1).
     * @param p
     * @param q
     * @param encryptionExponentGuess
     * @throws Exception
     */
    public void setup(BigInteger p, BigInteger q, BigInteger encryptionExponentGuess) throws Exception{
        this.p = p;
        this.q = q;

        //The keys must be prime, checking if they are
        if(!(p.isProbablePrime(1)) || !(q.isProbablePrime(1))){
            Exception e = new Exception("Both the public and private key must be prime");
            throw e;
        }

        //Calculating modulus, m = p*q
        this.m = p.multiply(q);
        System.out.println("modulus: " + this.m);

        /*Calculating the Encryption Exponent, which must be an integer with
        the greatest common factor of 1 with (privateKey -1)(publicKey -1)
         */
        int timer = 100;
        BigInteger temp = encryptionExponentGuess;

        /*Checks to see if the encryption exponent
        e is valid (gcd(e, (p-1)(q-1) == 1). If not, then e becomes the guess of e
        divided by the gcd of the guess and (p-1)(q-1). By cutting e up into groups
        of the greatest common factor, we ensure that the gcd is 1.
         */
            //gcd is set to the greatest common factor of the encryption guess and (p-1)(q-1)
            BigInteger gcd = temp.gcd(
                    p.subtract(new BigInteger("1")).multiply(
                            q.subtract(new BigInteger("1"))
                    )
            );

            //If it is equal to one, we are good, and the encryptionExponentGuess is valid.
            System.out.println("Encryption Exponent" + temp);
            if(gcd.intValue() == 1){
                this.e = temp;
            }else{
                //otherwise, we divide out the gcd and try again.
                this.e = temp.divide(gcd);
            }

        /*Calculating Decryption Exponent, which is the multiplicative inverse of
            the (publicKey - 1)(privateKey - 1)
         */
        //Calculating (publicKey - 1)(privateKey - 1)
        BigInteger pAndQ = p.subtract(new BigInteger("1")).multiply(
                q.subtract(new BigInteger("1"))
        );
        System.out.println("PandQ: " + pAndQ);

        //Using the build in Modulo inverse method of the BigInteger class
        this.d = e.modInverse(pAndQ);
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public void setM(BigInteger m) {
        this.m = m;
    }

    //Getter for the modulus, part of the public key (m, e)
    public BigInteger getM(){
        return this.m;
    }

    //Getter for the encryption exponent, part of the public key (m, e)
    public BigInteger getE(){
        return this.e;
    }

    /*********************************************************************
     * Encrypts a message using the public key (m, e). The encrypted
     * message is the message raised to the encryption exponent modulo
     * the modulus which is p * q where p and q are the large primes
     * chosen in the constructor of this object;
     * @param message to be encrypted (As a BigInteger)
     * @return an encrypted message (As a BigInteger)
     * @throws Exception
     **********************************************************************/
    public BigInteger encrypt(BigInteger message) throws Exception{
        if(message.toString().length() >= this.m.toString().length()){
            throw new Exception("The message must be shorter than the modulus by one digit, modulus is " +
            this.m.toString().length() + " digits long");
        }
        //Cipher-text = plain-text ^ e modulo m
        BigInteger encryptedMessage = message.pow(this.e.intValue()).mod(this.m);
        System.out.println(this.d);
        return encryptedMessage;
    }

    /*********************************************************************
     * Decrypts an encryption message by raising it to the decryption
     * exponent and modulo by the modulus which is p*q where q and q are
     * the large primes chosen within the constructor of this object.
     * @param the message to be decrypted (As a BigInteger)
     * @return the original message, now decrypted (As a BigInteger)
     * @throws Exception
     **********************************************************************/
    public BigInteger decyrpt(BigInteger encryptedMessage)throws Exception{
       /* if(encryptedMessage.toString().length() >= this.m.toString().length()){
            throw new Exception("The message must be shorter than the modulus by one digit, modulus is " +
                    this.m.toString().length() + " digits long");
        } */
        //plain-text = cipher-text ^ d mod m
        BigInteger message = encryptedMessage.pow(this.d.intValue()).mod(this.m);
        return message;
    }

    public static void main(String args[]){
        try {
            //RSA test = new RSA(new BigInteger("104059"), new BigInteger("102253"), new BigInteger("12"));
            RSA test = new RSA();
            test.setup(new BigInteger("104059"), new BigInteger("102253"), new BigInteger("40"));
            BigInteger secret = test.encrypt(new BigInteger("12345"));
            System.out.println("message: 12345");
            System.out.println("encrypted: " + secret.toString());
            System.out.println("Decrypted: " + test.decyrpt(secret));
        }catch (Exception e) {
            System.out.println("Error Caught: " + e.getMessage());
        }
    }


}
