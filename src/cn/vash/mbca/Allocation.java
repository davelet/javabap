package cn.vash.mbca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 
 * @author xiaodong
 */
public class Allocation {

	// final static int availableLength = 100;// available length of the quay
	static int totalCQ = 20;// total cq
	static ArrayList<Ship> ships = new ArrayList<Ship>();// �Ŷ���,��ѡ��Ĵ�ֻ���ƶ���shipping��
	static ArrayList<Ship> shipping = new ArrayList<Ship>();// ������
	// static ArrayList<Integer> berths = new ArrayList<Integer>();
	static boolean[] berths;
	static int TIME = 0;

	public static void main(String[] args) {

		// public Ship(int shipId, int goodsWeighted, int shipLength,int
		// arriveTime, int craneQuatity, int leaveTimeA)
		// berths.add(availableLength);
		berths = new boolean[100];
		for (int i = 0; i < 100; i++) {
			berths[i] = true;
		}
		System.out.println("���봬����: ");
		int n = 0;
		boolean key = true;
		while (key) {
			Scanner input = new Scanner(System.in);
			try {
				n = input.nextInt();
				key = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Please input a number...");
			}
		}
		Initialise(n);
		// Initialise(5);// ��ʼ��5����
		for (TIME = 0; TIME < 10; TIME++) {
			lookforTime();// Ϊ���絽���Ĵ�Ѱ�ҿ�ͣ������
			serving4S(shipping.size() - 1);
			if (shipping.size() == 0)
				break;
		}
	}

	private static void serving4S(int i) {
		// TODO Auto-generated method stub
		if (totalCQ < 1) {
			System.out.println("NO available cqs");
			return;
		}
		totalCQ -= 1;
		shipping.get(i).setCraneQuatity(1);
		for (int j = 0; j <= i; j++) {
			Ship ship = shipping.get(i);
			ship.setGoodsWeighted(ship.getGoodsWeighted() - 10
					* ship.getCraneQuatity());
			if (ship.getGoodsWeighted() <= 0) {
				System.out.println("�������" + ship.getShipId());
				totalCQ += ship.getCraneQuatity();
				ship.setCraneQuatity(0);
				shipping.remove(i);
			}
		}
	}

	private static void lookforTime() {
		// TODO Auto-generated method stub
		if (0 == ships.size())
			return;
		// boolean pickDone = false;
		if (allocateBerth(ships.get(0).getShipLength())) {// Ϊ��ǰ��ֻѰ�Ҳ�λ
			System.out
					.println("�Ѿ�Ϊ"
							+ shipping.get(shipping.size() - 1).getShipId()
							+ "�ҵ���λ...");
			System.out.println("�Ѿ����䲴λ...");
			return;
		}
		System.err
				.println("Not proper available berth for the ship, Please wait...");
	}

	private static boolean allocateBerth(int shipLength) {
		// TODO Auto-generated method stub
		int i = 0;
		do {
			if (berths[i] == true) {
				int j;
				for (j = i; j < i + shipLength; j++) {
					if (berths[j] == false) {
						i = j + 1;
						break;
					}
				}
				if (j == i + shipLength) {
					while (j >= i) {
						berths[j] = false;
						j--;
					}
					ships.get(0).setStation(i);
					System.out.println(ships.get(0).getShipId()+"�Ŵ��Ѿ����䣬��ʼ�ڲ�λ"+i);
					shipping.add(ships.get(0));
					ships.remove(0);
					return true;
				}
			} else {
				i++;
			}
		} while (i < 100);
		return false;
	}

	private static void Initialise(int i) {// ��ʼ��i����
		// TODO Auto-generated method stub
		for (int j = 0; j < i; j++) {
			int arr = (int) (Math.random() * 10);
			Ship ship = new Ship(j,
					(int) ((30 + Math.random() * 70) / 10) * 10,
					(int) (10 + Math.random() * 40), arr,
					(int) (1 + Math.random() * 6), -1, arr// -1��ʾδ����
							+ (int) (50 + Math.random() * 50));
			ships.add(ship);
		}
		System.out.println("id     " + "Atime    " + "length   " + "weight    "
				+ "cq    " + "LT_a" + "   //Atime����ʱ�䣬LT_a�����뿪ʱ�䣬LT_bʵ���뿪ʱ��");
		System.out.println("----------------------------------------------");
		for (int j = 0; j < i; j++) {
			System.out.print(ships.get(j).getShipId() + "  |    "
					+ ships.get(j).getArriveTime() + "  |    "
					+ ships.get(j).getShipLength() + "  |    "
					+ ships.get(j).getGoodsWeighted() + "  |    "
					+ ships.get(j).getCraneQuatity() + "  |    "
					+ ships.get(j).getLeaveTimeA() + "\n");
		}
		Comparator<Ship> comparator = new Comparator<Ship>() {
			@Override
			public int compare(Ship s1, Ship s2) {
				// ����ʱ��
				if (s1.getArriveTime() != s2.getArriveTime()) {
					return s1.getArriveTime() - s2.getArriveTime();
				} else {
					return s1.getShipLength() - (s2.getShipLength());
				}
			}
		};
		Collections.sort(ships, comparator);// Ϊ������ʱ��ͳ�������
		System.out.println("----------------------------------------");
		for (int j = 0; j < i; j++) {
			System.out.print(ships.get(j).getShipId() + "  |    "
					+ ships.get(j).getArriveTime() + "  |    "
					+ ships.get(j).getShipLength() + "  |    "
					+ ships.get(j).getGoodsWeighted() + "  |    "
					+ ships.get(j).getCraneQuatity() + "  |    "
					+ ships.get(j).getLeaveTimeA() + "\n");
		}
	}
}