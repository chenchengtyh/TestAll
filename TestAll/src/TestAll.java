import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class TestAll {

	public static void main(String[] args) {

		// 计算年假
		// CalcuToHoliday();

		// s=a+aa+aaa+... a与相加个数手动输入
		Add();

	}

	@SuppressWarnings("resource")
	private static void Add() {
		// TODO Auto-generated method stub

		System.out.println("输入相加的值:");
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		System.out.println("输入相加个数");

		int count = in.nextInt();
		int add;
		add = calcu(a, count);
		System.out.print(add);

	}

	private static int calcu(int a, int count) {
		// TODO Auto-generated method stub

		if (count == 0) {
			System.out.print("=");
			return 0;
		} else {

			int y = 0;
			int k = a;
			for (int i = 1; i <= count; i++) {
				int l = i;
				k = a;
				while (l > 1) {
					k = k * 10;
					l--;
				}
				y += k;
			}

			if (count == 1) {
				System.out.print(y);
			} else {
				System.out.print(y + "+");
			}

			return y + calcu(a, count - 1);
		}

	}

	private static void CalcuToHoliday() {
		// TODO Auto-generated method stub
		try {
			// 时间转换类
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse("2016-5-1");
			Date date2 = sdf.parse("2017-5-1");
			// 将转换的两个时间对象转换成Calendard对象
			Calendar can1 = Calendar.getInstance();
			can1.setTime(date1);
			Calendar can2 = Calendar.getInstance();
			can2.setTime(date2);
			// 拿出两个年份
			int year1 = can1.get(Calendar.YEAR);
			int year2 = can2.get(Calendar.YEAR);
			// 天数
			int days = 0;
			Calendar can = null;
			// 如果can1 < can2
			// 减去小的时间在这一年已经过了的天数
			// 加上大的时间已过的天数
			if (can1.before(can2)) {
				days -= can1.get(Calendar.DAY_OF_YEAR);
				days += can2.get(Calendar.DAY_OF_YEAR);
				can = can1;
			} else {
				days -= can2.get(Calendar.DAY_OF_YEAR);
				days += can1.get(Calendar.DAY_OF_YEAR);
				can = can2;
			}
			for (int i = 0; i < Math.abs(year2 - year1); i++) {
				// 获取小的时间当前年的总天数
				days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
				// 再计算下一年。
				can.add(Calendar.YEAR, 1);
			}
			System.out.println("天数差：" + days);
			System.out.print("年假天数:" + ((float) days / 365 * 5));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
