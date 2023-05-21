package com.wechat;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class Execute
{

    private WeChatRobot robot = new WeChatRobot();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void everydayimage(String friendName, String timeStr, String type, String msg) throws ParseException {
        // 获取目标时间
        Date date = getDate(timeStr);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                // 如果时间超过早上6点，并且要大于前一天的 22点 就结束
                System.out.println("date.getMinutes()==" + new Date().getHours());
                Date now = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                calendar.set(Calendar.DATE, calendar.get(Calendar.HOUR) - 8);
                Date bfdate = calendar.getTime();
                if (now.getHours() > 6 && bfdate.getHours() > 22) {
                    timer.cancel();
                    System.out.println("打卡任务结束");
                }
                else {
                    printLog(friendName, "消息类型" + type);
                    robot.OpenWeChat();
                    robot.ChooseFriends(friendName);
                    if ("image".equals(type)) {
                        int i = IntegerAction.getCnt();
                        System.out.println("interaction.getCnt()=" + i);
                        File imgFile = null;
                        imgFile = new File("D:\\wechatjobconf\\job" + i + ".png");
                        Image image = null;
                        try {
                            image = ImageIO.read(imgFile);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            robot.SendMessageImage(image);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if ("string".equals(type)) {
                        robot.SendMessage(msg);
                    }
                }
            }
        };
        // 一小时跑一次
        timer.schedule(timerTask, date, 1000 * 60 * 60);

    }

    public void everyday(String friendName, String timeStr, String message) throws ParseException {
        // 获取目标时间
        Date date = getDate(timeStr);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                // 如果时间超过6点，就结束
                // System.out.println("date.getHours()==" + date.getHours());
                printLog(friendName, message);
                robot.OpenWeChat();
                robot.ChooseFriends(friendName);
                robot.SendMessage(message);
            }
        };
        // 一小时跑一次
        timer.schedule(timerTask, date, 1000 * 60);

    }

    private void printLog(String friendName, String message) {
        System.out.println("-----------------发送消息-----------------");
        System.out.println("当前时间: " + sdf.format(new Date()));
        System.out.println("发送对象: " + friendName);
        System.out.println("发送内容: " + message);
    }

    private Date getDate(String timeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前日期 例如 2020-22-22
        String currentDate = sdf.format(new Date());
        // 组拼 目标时间 2020-22-22 22:22:22
        String targetTime = currentDate + " " + timeStr;

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 目标时间 时间戳
        long targetTimer = sdf.parse(targetTime).getTime();
        // 当前时间 时间戳
        long currentTimer = new Date().getTime();
        // 判断是否已过目标时间
        if (targetTimer < currentTimer) {
            // 目标时间加一小时
            targetTimer += 1000 * 60 * 60;
        }
        // 返回目标日期
        return new Date(targetTimer);
    }

}
