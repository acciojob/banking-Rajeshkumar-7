package com.driver;

import java.util.PriorityQueue;

public class CurrentAccount extends BankAccount{
    String tradeLicenseId; //consists of Uppercase English characters only

    public CurrentAccount(String name, double balance, String tradeLicenseId) throws Exception {
        // minimum balance is 5000 by default. If balance is less than 5000, throw "Insufficient Balance" exception

        super(name , balance , 5000);

        if(balance < 5000){
            throw new Exception("Insufficient Balance");
        }
        this.tradeLicenseId = tradeLicenseId;
    }

    public void validateLicenseId() throws Exception {
        // A trade license Id is said to be valid if no two consecutive characters are same
        // If the license Id is valid, do nothing
        // If the characters of the license Id can be rearranged to create any valid license Id
        // If it is not possible, throw "Valid License can not be generated" Exception
        char[] arr = this.tradeLicenseId.toCharArray();
        int n = arr.length;
        if(n == 1 || isValid(arr)) return;

        PriorityQueue<Pair> pq = new PriorityQueue<Pair>((a,b) -> b.freq - a.freq);
        int[] freq = new int[26];

        for(int i=0;i<n;i++){
            freq[arr[i] - 'A']++;
        }

        for(int i=0;i<26;i++){
            pq.add(new Pair((char)(i + 'A') , freq[i]));
        }

        StringBuilder sb = new StringBuilder();
        while(pq.size() > 0){
            Pair p = pq.remove();
            sb.append(p.c);
            p.freq--;
            boolean flag = false;
            if(!pq.isEmpty()){
                Pair p2 = pq.remove();
                sb.append(p2.c);
                p2.freq--;
                flag = true;
                if(p.freq > 0)pq.add(p);
                if(p2.freq > 0)pq.add(p2);
            }
            if(flag) continue;
            if(p.freq > 0) pq.add(p);
        }

        String id = sb.toString();
        char[] ans = id.toCharArray();
        if(isValid(ans)){
            this.tradeLicenseId = id;
            return;
        }else{
            throw new Exception("Valid License can not be generated");
        }
    }

    private boolean isValid(char[] arr){
        int n = arr.length;
        for(int i=1;i<n;i++)
            if (arr[i - 1] == arr[i]) {
                return false;
            }
        return true;
    }

    class Pair{
        char c;
        int freq;
        Pair(char c , int freq){
            this.c = c;
            this.freq = freq;
        }
    }

    public String getTradeLicenseId() {
        return tradeLicenseId;
    }

    public void setTradeLicenseId(String tradeLicenseId) {
        this.tradeLicenseId = tradeLicenseId;
    }
}
