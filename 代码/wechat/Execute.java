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
        // ��ȡĿ��ʱ��
        Date date = getDate(timeStr);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                // ���ʱ�䳬������6�㣬����Ҫ����ǰһ��� 22�� �ͽ���
                System.out.println("date.getMinutes()==" + new Date().getHours());
                Date now = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                calendar.set(Calendar.DATE, calendar.get(Calendar.HOUR) - 8);
                Date bfdate = calendar.getTime();
                if (now.getHours() > 6 && bfdate.getHours() > 22) {
                    timer.cancel();
                    System.out.println("���������");
                }
                else {
                    printLog(friendName, "��Ϣ����" + type);
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
        // һСʱ��һ��
        timer.schedule(timerTask, date, 1000 * 60 * 60);

    }

    public void everyday(String friendName, String timeStr, String message) throws ParseException {
        // ��ȡĿ��ʱ��
        Date date = getDate(timeStr);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                // ���ʱ�䳬��6�㣬�ͽ���
                // System.out.println("date.getHours()==" + date.getHours());
                printLog(friendName, message);
                robot.OpenWeChat();
                robot.ChooseFriends(friendName);
                robot.SendMessage(message);
            }
        };
        // һСʱ��һ��
        timer.schedule(timerTask, date, 1000 * 60);

    }

    private void printLog(String friendName, String message) {
        System.out.println("-----------------������Ϣ-----------------");
        System.out.println("��ǰʱ��: " + sdf.format(new Date()));
        System.out.println("���Ͷ���: " + friendName);
        System.out.println("��������: " + message);
    }

    private Date getDate(String timeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // ��ȡ��ǰ���� ���� 2020-22-22
        String currentDate = sdf.format(new Date());
        // ��ƴ Ŀ��ʱ�� 2020-22-22 22:22:22
        String targetTime = currentDate + " " + timeStr;

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Ŀ��ʱ�� ʱ���
        long targetTimer = sdf.parse(targetTime).getTime();
        // ��ǰʱ�� ʱ���
        long currentTimer = new Date().getTime();
        // �ж��Ƿ��ѹ�Ŀ��ʱ��
        if (targetTimer < currentTimer) {
            // Ŀ��ʱ���һСʱ
            targetTimer += 1000 * 60 * 60;
        }
        // ����Ŀ������
        return new Date(targetTimer);
    }

}
