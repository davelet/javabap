package cn.vash.mbca;

public class Ship {
	private int goodsWeighted;// weight
	private int shipLength;
	private int arriveTime;
	private int craneQuatity;
	private int shipId;// ship number
	private int leaveTimeA;
	private int station;


	public Ship(int shipId, int goodsWeighted, int shipLength, int arriveTime,
			int craneQuatity, int station, int leaveTimeA) {
		// TODO Auto-generated constructor stub
		this.shipId = shipId;
		this.goodsWeighted = goodsWeighted;
		this.shipLength = shipLength;
		this.arriveTime = arriveTime;
		this.craneQuatity = craneQuatity;
		this.station = station;
		this.leaveTimeA = leaveTimeA;
	}

	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}

	public int getGoodsWeighted() {
		return goodsWeighted;
	}

	public void setGoodsWeighted(int goodsWeighted) {
		this.goodsWeighted = goodsWeighted;
	}

	public int getShipLength() {
		return shipLength;
	}

	public void setShipLength(int shipLength) {
		this.shipLength = shipLength;
	}

	public int getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}

	public int getCraneQuatity() {
		return craneQuatity;
	}

	public void setCraneQuatity(int craneQuatity) {
		this.craneQuatity = craneQuatity;
	}

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public int getLeaveTimeA() {
		return leaveTimeA;
	}

	public void setLeaveTimeA(int leaveTimeA) {
		this.leaveTimeA = leaveTimeA;
	}

}
