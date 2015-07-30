package com.example.ahtcfg24.myapplication;

import java.util.Scanner;

/**
 * <p>Description: </p>
 *
 * @author XuDing
 * @version 1.0
 * @date 2015/7/30
 */
public class Caculator
{
    public static void main(String[] args)
    {
        double px;
        double dpi = 0;
        double dp = 0;
        double inch;

        px=dp*(dpi/160);
        Scanner scan = new Scanner(System.in);
        System.out.print("dp:");
        dp=scan.nextDouble();
        System.out.print("dpi:");
        dpi=scan.nextDouble();
        System.out.println(px);
    }
}
