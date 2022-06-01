package com.atguigu.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class JedisDemo2 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入你的手机号码");
        String phone = scanner.nextLine();
        Jedis jedis=new Jedis("127.0.0.1",6379);

        String code = getCode();
        System.out.println("验证码是："+code+"已存放至redis中");
        jedis.set("phone",phone);
        jedis.set("code",code+"");

        System.out.println("请填写收到的验证码");
        String r_code = scanner.nextLine();
        if (r_code.equals(jedis.get("code"))){
            System.out.println("成功");
            return;
        }
        System.out.println("失败");



    }
    public static String getCode(){

        Random random=new Random();
        StringBuilder code=new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        System.out.println(code);
        String code1=code.toString();
        return code1;

    }
    //每个手机每天只能发送三次验证码 ，验证码放到redis中 每个2分钟过期时间
    public static void verifyCode(String phone){

        Jedis jedis=new Jedis("127.0.0.1",6379);
        String countKey="Count"+phone;
        String codeKey="code"+phone;
        String count=jedis.get(codeKey);
        if (count==null){
            //没有发送次数，第一次发送
            jedis.setex(countKey,24*60*60,"1");
        } else if (Integer.parseInt(countKey)<=2){
            jedis.incr(codeKey);
        }else if (Integer.parseInt(codeKey)>2){
            //发送次数上限
            System.out.println("今天发送次数已超过三次");
            jedis.close();
            return;
        }
        //验证码存入redis
        String code=getCode();
        jedis.setex(codeKey,120,code);
        jedis.close();

    }
    public static void getRedisCode(String phone,String code){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        String codeKey="code"+phone;
        String countKey="Count"+phone;

        String Rcode = jedis.get(codeKey);
        if (Rcode.equals(code)){
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }
        String count = jedis.get(countKey);
        System.out.println("count = " + count);
        jedis.close();

    }
    @Test
    public void test(){

        //模拟验证码发送
        //verifyCode("13175169151");
       getRedisCode("13175169151","873581");
    }
}