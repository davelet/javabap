package cn.vash.mbca;

//*使用分支定界法进行计算的泊位分配程序*/
//使用列表动态存储数据，不存在越界问题
//分支定界法采用先进先出的广度优先策略进行遍历
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import java.io.*;
import java.awt.*;

/*SIM类中包含单纯形计算和分支定界计算等方法*/
class SIM
{
	int l,e;
	int i,j,k1,k2,m,n;
	float K;
	
	//PIOVT单纯形中的基变换操作，返回交换以后的N,B,a,b,c,x的值
	public ArrayList<ArrayList> PIOVT(int N[],int B[],float a[][],float b[],float c[],float v[],float x[],int l,int e,int t)//使用列表返回多个变量值
		{ ArrayList<int[]> l1=new ArrayList<int[]>();
		  ArrayList<float[][]> l2=new ArrayList<float[][]>();
		  ArrayList<float[]> l3=new ArrayList<float[]>();
		  ArrayList<ArrayList> l4=new ArrayList<ArrayList>();
			l1.add(N);
			l1.add(B);
			l2.add(a);
			l3.add(b);
			l3.add(c);
			l3.add(x);
			l4.add(l1);
			l4.add(l2);
			l4.add(l3);
		 b[e]=b[l]/a[l][e];//计算交换后换入变量Xe前面的系数
		for(k1=0;k1<N.length;k1++)//计算Xe中各个X前面交换后生成的新系数
		{
			if(N[k1]!=e)
			{
			 j=N[k1];
			 a[e][j]=a[l][j]/a[l][e];
			}
			else
				continue;
		}

		 a[e][l]=1/a[l][e];
		 //以上得到交换后xe方程的ab系数
		 //下面的if函数：如果换入变量e不等于0.则对x0方程的a,b单独计算
		 if(e!=0)
		 {
			 for(k1=0;k1<B.length;k1++)
			 {
				 if(B[k1]==0)
				 {
					 b[0]=b[0]-a[0][e]*b[e];
					 for(k2=0;k2<N.length;k2++)
						{
							if(N[k2]!=e)
							{j=N[k2];
							 a[0][j]=a[0][j]-a[0][e]*a[e][j];
							}
							
						}
					a[0][l]=-a[0][e]*a[e][l];
				    break;
				 }
			 }
		 }
		//将换入变量xe的ab系数带入其他约束式中，替换等式右边的每次出现，更新其他等式。
		for(k1=0;k1<B.length;k1++)
		{
			if(B[k1]!=l&&B[k1]!=0)
			{i=B[k1];
			
			b[i]=b[i]-a[i][e]*b[e];
			for(k2=0;k2<N.length;k2++)
			{
				if(N[k2]!=e&&B[k1]!=0)
				{
					j=N[k2];
				    a[i][j]=a[i][j]-a[i][e]*a[e][j];
				}
				else
					continue;
			}
			a[i][l]=-a[i][e]*a[e][l];
			}
			else
				continue;
				
		}
		v[0]=v[0]+c[e]*b[e];
		
		//交换操作后，c的系数因交换操作改变
		for(k1=0;k1<N.length;k1++)
		{
			if(N[k1]!=e)
			{
				j=N[k1];
			    c[j]=c[j]-c[e]*a[e][j];}
			else
				continue;
		}
		c[l]=-c[e]*a[e][l];  //新换出的l在目标函数中的系数值
		b[l]=0;  //换出变量的b系数变为0
		c[e]=0;  //换入变量的c系数变为0
		for(i=0;i<N.length;i++)//换出变量l的原方程的各项系数变为0——a
		{
			j=N[i];
			a[l][j]=0;
		
		}
		for(i=0;i<B.length;i++)//换入变量e的原方程的各项系数变为0——b
		{
			j=B[i];
			a[j][e]=0;
		}
	 for(k1=0;k1<N.length;k1++)//把换出变量l添入N中
	 {
	 	if(N[k1]==e)
	 	{N[k1]=l;
	 	break;}
	}
	for(k2=0;k2<B.length;k2++)//将换入变量e添入B中
	{
		if (B[k2]==l)
		{ B[k2]=e;
		break;}
	}
	return l4;
	}



	//INITIALILE_SIMPLEX是对整个线性规划判断是否可行,然后进行初始化返回NB数组值
	public ArrayList<int[]> INITIALILE_SIMPLEX(int N[],int B[],float a[][],float b[],float c[],float v[],float x[],int t)
		{
			
		    float K=Integer.MAX_VALUE;
		    float E=Integer.MAX_VALUE;
		    float M[]=new float[t];
	          float d[]=new float[t];
			ArrayList<int[]> l1=new ArrayList<int[]>();
			l1.add(N);
			l1.add(B);
			 m=0; n=0;
			
			/*   根据规划方程的a，b，c得出数组B[]
			 *   只要有a[i][j]不等于0，就将i加入到数组B中
			 *       相同的i只加入一次                       */
			 for( i=0;i<a.length;i++)
				{
					
					for( j=0;j<a[i].length;j++)
					{
						
						if (a[i][j]!=0)
							{
							B[m]=i;
							m++;
							break;
							}
						
					}
				}
			 
			 /*根据规划方程的a，b，c得出数组N[]
			  *  只要有a[i][j]不等于0，检查数组N中是否含有j
			  *  如果有就继续下一个a[i][j]，否则就将j加入到数组N中
			  *      相同的j只加入一次                       */
				 for(i=0;i<a.length;i++)
					{
					 for( j=0;j<a[i].length;j++)
					  {
						if (a[i][j]!=0)
						{
							for( k1=n;k1>=0;k1--)
								{if(N[k1]!=j)
								   {
									K=k1;
									continue;
									}
									
								else
									break;
								}
							if(K==0)
							{
								N[n]=j;
							    n++;
							    continue;
							}
							else
								continue;
							
								}
						else
							continue;
							}
					} 
				 
				//求b[i]的最小值E
			 for(i=0;i<b.length;i++)
				{
					if(b[i]<E)
						{
						E=b[i];
					    l=i;
					    }
				}
			 
			 /*************************************
			  * 若b[i]的最小值E<0则进行辅助线性规划
			  * 首先目标函数变为x0，并将x0加入到各个方程当中
			  **************************************/
			 if(E<0) 
			{
				// 将x0加入到各个方程式中
				for(i=0;i<B.length;i++)
				{
					if (B[i]!=0)
					{
						j=B[i];
					    a[j][0]=-1;
				    }
				}
				//将0加到N中
				for(i=N.length-1;i>0;i--)
			          N[i]=N[i-1];	
				N[0]=0;
				//将目标函数设为x0，并将原有的系数c暂存如d中
			    for(i=0;i<c.length;i++)
			      { 
			    	d[i]=c[i];
				    c[i]=0;
				   }
			    c[0]=-1;
			    //0为换入变量，l为换出变量，进行基变换
				PIOVT(N,B,a,b,c,v,x,l,0,t);
				j=0;
				k2=0;
				
				//进行多次基变换操作求得最后x[0]的值
		loop:while(k2<N.length)
			{
			//如果N值中有负值，将x0值设为0，并结束计算
			   if(N[k2]<0) 
			   {
				   x[0]=-1;
				   break;
			   }
			  //否则继续交换等操作，直到得到最后结果
			   else  
			   {
				   j=N[k2];
				 //选择目标函数中系数为正的变量作为换入变量e，并进行基变换操作
					  if (c[j]>0)
						{
						    e=j;
							l=Integer.MAX_VALUE;
							K=Integer.MAX_VALUE;
							for(i=0;i<B.length;i++)//确定换出变量l
							{
								k1=B[i];
								if (a[k1][e]>0)
								{
									M[k1]=b[k1]/a[k1][e];
									if (M[k1]<K)//取最严格限制xe增加的数，即取M[k1]的最小值
									{
										K=M[k1];
										l=k1;
									}
								}
								
							}
				          //如果无法确定换出变量l的值，线性规划无界，并将x0的值设为-1，数组N的值全部设为0
							if (K==Integer.MAX_VALUE)
							{
								System.out.println("IN-unbounded");//无界
								x[0]=-1;
								for(i=0;i<N.length;i++)
									N[i]=0;
							    break;
							 }
							//否则继续计算，进行基变换，并且基变换以后需要重新进行初始化
							else  //(K!=Integer.MAX_VALUE)
							{
								if(a[l][e]!=0)
							   {
									PIOVT(N,B,a,b,c,v,x,l,e,t);
							     for(i=0;i<N.length;i++)
								    N[i]=0;
							     for(i=0;i<B.length;i++)
							  	   B[i]=0;
							     INITIALILE_SIMPLEX(N,B,a,b,c,v,x,t);
							     k2=0;
							     continue loop;
							   }
							else   //(a[l][e]==0)
								k2++;
							}
							}//if (c[j]>0)
					
							else
								k2++;
					           
			   }//else ，计算x0的值
				 
			} //loop:while
				//System.out.println("loop stop!!辅助线性规划结束");	
				//得出x的值
				 for(i=0;i<N.length;i++)
				  	{
				  		if(N[i]>0)
				  		{   j=N[i];
				  		    x[j]=0;
				  		}
				  	}
				  	for(i=0;i<B.length;i++)
				  	{
				  		if(B[i]>0)
				  		{
				  			j=B[i];
				  			x[j]=b[j];
				  		}
				  	}
			/*************************************************
			 * 如果x0不等于0，则输出不可行，并将B的系数全部设为0
			 * 否则消去各方程式中的x0，目标函数转为原目标函数
			 * **********************************************8*/
			if(x[0]!=0)
			{
				System.out.println("infeasible");
				for(i=0;i<B.length;i++)
					B[i]=0;
				
			}
			 else if(x[0]==0)  //(x[0]==0)原规划可行,消除方程中的x0
			{
				int B1[]=new int[t];
				j=0;
				for(i=0;i<d.length;i++)//B1中存入原始的非基本变量
				{
					if(d[i]!=0)
					{
						B1[j]=i;
						j++;
					}
						
				}
				for(i=0;i<d.length;i++)//将c的系数从d中取出，改为原始数据
				{
					for(j=0;j<B.length;j++)
					{
						if(i!=B[j])
						{
							c[i]=d[i];
						}
					}
				}
				//目标函数系数
				for(i=0;i<B1.length;i++)
				{
					for(j=0;j<B.length;j++)
					{
						if(B[j]==B1[i]&&B[j]!=0)
						{
							k2=B[j];
							v[0]=v[0]+b[k2]*d[k2];
							for(k1=1;k1<a[k2].length;k1++)
							{
								if(a[k2][k1]!=0)
									c[k1]=c[k1]-a[k2][k1]*d[k2];
							}
							c[k2]=0;
						}
						else
							continue;
					}
				}
				//x0的ab系数都变为0
				for(i=0;i<a.length;i++)
					a[i][0]=0;
				for(i=0;i<a[0].length;i++)
					a[0][i]=0;
				b[0]=0;
				m=0;
				for( i=0;i<a.length;i++)//得出数组B[]
				{
					
					for( j=0;j<a[i].length;j++)
					{
						
						if (a[i][j]!=0)
							{
							B[m]=i;
							m++;
							break;
							}
						else
							continue;
						
					}
				}
				 for(i=0;i<a.length;i++)//得出数组N[]
					{
					 for( j=0;j<a[i].length;j++)
					  {
						if (a[i][j]!=0)
						{
							for( k1=n-1;k1>=0;k1--)
								{if(N[k1]!=j)
								   {
									K=k1;
									continue;
									}
									
								else
									break;
								}
							if(K==0)
							{N[n]=j;
							n++;
							continue;}
							else
								continue;
							
								}
						else
							continue;
							}
					} 
			} //(x[0]==0)原规划可行
		}//辅助线性规划
		
				return l1;

		}

  /************************************************************************8
   * 方法SIMPLEX是单纯形法的主方法，在SIMPLEX中分别调用基变换和初始化方法
   * 求得最终结果，并返回x的值
   * ***************************************************************************/
	public  float[] SIMPLEX(int N[],int B[],float a[][],float b[],float c[],float v[],float x[],int t)
	{
		float M[]=new float[t];
		//首先，进行初始化
		INITIALILE_SIMPLEX(N,B,a,b,c,v,x,t);
		 j=-Integer.MAX_VALUE;
		 k2=-Integer.MAX_VALUE;
		 
		 //分别求得数组N，B的最小值，判断规划是否可行
		 for(i=0;i<N.length;i++)
		 {
			 if(N[i]>j)
				 j=N[i];
		 }
		 for(i=0;i<B.length;i++)
		 {
			 if(B[i]>k2)
				 k2=B[i];
		 }
		 if(j==0||k2==0)
		 {
			 for(i=0;i<x.length;i++)
				 x[i]=0;
		 }
		 //行行规划可行
		 else if(j!=0&&k2!=0)
		 {
			    j=0;
				k2=0;	 
			loop:while(k2<N.length)
			{
			  j=N[k2];
			  if (c[j]>0&&j!=0)//选择目标函数中系数为正的变量作为换入变量e
				{
				     e=j;
					l=Integer.MAX_VALUE;
					K=Integer.MAX_VALUE;
					for(i=0;i<B.length;i++)//确定l
					{
						k1=B[i];
						if (a[k1][e]>0)
						{
							M[k1]=b[k1]/a[k1][e];
							if (M[k1]<K)//取最严格限制xe增加的，即取M[k1]的最小值
							{
								K=M[k1];
								l=k1;
							}
						}
						
					}
					if (K==Integer.MAX_VALUE)
					{
						System.out.println("SIM-unbounded");
						for(i=0;i<N.length;i++)
							N[i]=0;
					     break;
					}
					else
					{
						if(a[l][e]!=0)
					    {
							PIOVT(N,B,a,b,c,v,x,l,e,t);//调用基变换方法
					
					for(i=0;i<N.length;i++)
					{
						N[i]=0;
					}
					for(i=0;i<B.length;i++)
					{
						B[i]=0;
					}
					//将N，B设为0后，重新初始化
					INITIALILE_SIMPLEX(N,B,a,b,c,v,x,t);
					k2=0;
					continue loop;
					}
					else
						k2++;
					}
					}
			
					else
						k2++;
			           
			}
		 }//线性规划可行
		 for(i=0;i<x.length;i++)
			 x[i]=0;
			
		 for(i=0;i<N.length;i++)
	  	{
	  		if(N[i]>0)
	  		{   j=N[i];
	  		    x[j]=0;
	  		}
	  	}
	  	for(i=0;i<B.length;i++)
	  	{
	  		if(B[i]>0)
	  		{
	  			j=B[i];
	  			x[j]=b[j];
	  		}
	  	}
	 	 return x;
	 	
		}

//方法SI将SIM生成的x值检查，保留小数点后两位，并返回
public float[] SI(int N[],int B[],float a[][],float b[],float c[],float v[],float x[],int t)
{
	ArrayList<int[]> l1=new ArrayList<int[]>();
	ArrayList<float[]> l2=new ArrayList<float[]>();
	ArrayList<ArrayList> l3=new ArrayList<ArrayList>();
	l1.add(N);
	l1.add(B);
	l2.add(x);
	l3.add(l1);
	l3.add(l2);
	  SIMPLEX(N,B,a,b,c,v,x,t);
	        l1=l3.get(0);
	        l2=l3.get(1);
	        N=l1.get(0);
	        B=l1.get(1);
	        x=l2.get(0);
	        int p=-Integer.MAX_VALUE,q=-Integer.MAX_VALUE;
	    	for(i=0;i<N.length;i++)
	    	{
	    		if(p<N[i])
	    			p=N[i];
	    	}
	    	for(i=0;i<B.length;i++)
	    	{
	    		if(q<B[i])
	    			q=B[i];
	    	}	
	    	if(p==0||q==0) //线性规划不可行或者无界
	    	{
	    		for(i=0;i<x.length;i++)
	    		{
	    			x[i]=0;
	    		}
	    		v[0]=-Integer.MAX_VALUE;
	    	}
	    	else
	    	{
	    		 for(i=0;i<N.length;i++)
	 	     	{
	 	     		if(N[i]!=0)
	 	     		{   j=N[i];
	 	     		   x[j]=0;
	 	     		}
	 	     	}
	 	     	for(i=0;i<B.length;i++)
	 	     	{
	 	     		if(B[i]!=0)
	 	     		{
	 	     			j=B[i];
	 	     			x[j]=b[j];
	 	     		}
	 	     	}
	    	}
	    	System.out.println("x[0]="+v[0]);
	    	for(i=0;i<x.length;i++)//将结果保留两位小数。
			   {
		  		x[i]=(float)((int)(x[i]*100+0.5))/100;
			   }
	    //	v[0]=(float)((int)(v[0]*100+0.5))/100;
	    	v[0]=Math.round(v[0]);
	     return x;
}

//一个求数组最大值的小函数
public  float max(float v[]) 
{
	float max=-Integer.MAX_VALUE;
	int i;
	for(i=0;i<v.length;i++)
	{
		if(v[i]>max)
		{
			max=v[i];
		}
	}
	return max;
}

/********************************************************************************
 * 方法branch是分支定界法，多次调用单纯形法进行计算，
 * 直到找到一个整数解，并将这个整数解返回
 * x11和v11分别用来保存x和v的最后结果
 * A1是船总数，t2为非基本变量的个数，t1是基本变量与非基本变量之和
 * */
public ArrayList<float[]> Branch(int A1,int T,int M,int TS[],int L[],int TW[],int N[],int B[],float a[][],float b[],float c[],float v[],float x[],int t1,int t2)
{
	int p=0,q,m1,n1;
	int t;
	int ia,ib;
	float k=-1,k1;
	float M1;
	int k2=5000;  //计算的次数
	 t=t1+100;   //添加分支的层数,此处设添加分支的层数的上限为100
	float x1[]=new float[t];//保存结果的整数值，用于结果是否为整数值的判断
	float x2[]=new float[t];//保存先进行计算的某侧分支的结果
	float x11[]=new float[t1];//线性规划最大值时各个x的取值,最终结果
	float v11[]=new float[1];//线性规划的最大值v
	float vl=0,vr=0;
	float vn;//每次计算时用于保存v的值
	ArrayList<Integer>l1=new ArrayList<Integer>();  //i
	ArrayList<Float> l2=new ArrayList<Float>();     //x[i]
	ArrayList<Float> la=new ArrayList<Float>(); 
	ArrayList<Float> lb=new ArrayList<Float>(); 
	ArrayList<Float> l5=new ArrayList<Float>();      //v
	ArrayList<float[]> l6=new ArrayList<float[]>();
	l6.add(x11);
	l6.add(v11);
	i=1;
	j=0;
	int aa[][]=new int[T+1][M+1];
	float v22[]=new float[k2];//记录每个计算结果
	float minz=0,maxz=-Integer.MAX_VALUE;
	float minzfio=0;
	float xfio[]=new float[t1];//先到先服务时各个x的取值
	float vfio[]=new float[1];//先到先服务最大值v
	//float min,max;
		float a1[][]=new float[t][t];
		float a2[][]=new float[100][t2+1];  //列表到数组
		float al[][]=new float[t][t];
		float ar[][]=new float[t][t];
	//	float a0[][][]=new float[k2][100][t2+1];//a0[1][2][3]中1远大于2，3等于t1.
		
		float b1[]=new float[t];
		float b2[]=new float[100];//列表到数组
		float bl[]=new float[t];
		float br[]=new float[t];
		//float b0[][]=new float[k2][100];
		
		float c1[]=new float[t];
	        SIM s=new SIM();
	     //先采用先到先服务的原则计算一个全为整数的minz值，减少以后的计算次数，可以有效地提高效率
	        for(i=0;i<=T;i++)  //将整个空间初始化为0，放入船舶后则改为1
	        {
	        	for(j=0;j<=M;j++)
	        	{
	        		aa[i][j]=0;
	        	}
	        }
	        x[1]=TS[1];   //先将船1固定，然后其他的船在这个基础上安排
	        x[1+A1]=1;
	        for(i=(int)x[1];i<(x[1]+TW[1]);i++)
	        {
	        	for(j=1;j<1+L[1];j++)
	        	{
	        		aa[i][j]=1;
	        	}
	        }
	        
	   loop1: for(i=2;i<=A1;i++)   //其他船只的安排
	       {
	    	   System.out.println("船"+i);  
	    	   m=TS[i];
	    loop2:  while(m<=T)
	    	  {
	    		 System.out.println("m="+m);  
	    		  n=1;
	    		  n1=0;
	    		loop: while(n<=M)
	    		  { 
	    			  if(m>T)
	    			  {
	    				  for(q=0;q<x.length;q++)
	    					 {
	    						 x[q]=0;
	    					 }
	    					 m=T+1;
	    					 continue loop2;
	    			  }
	    			  if(aa[m][n]==1)
	    			  {
	    				  n1=0;
	    				  n++;
	    				  if(n>M-L[i]+1)
	  	    			 {
	  	    				m++;
	  	    				continue loop2;
	  	    			 }
	    			  }
	    			  else
	    			  {
	    				  n++;
	    				  n1++;    //计数，并与船长比较	    				  
	    			  }
	    			//  System.out.println("n="+n);  
	    			 // System.out.println("n1="+n1);  
	    			  if(n1==L[i])   //在横轴上有足够的空间
	    			  {
	    				  System.out.println("在横轴上有足够的空间");
	    				  m1=0;
	    				  if(m+TW[i]>T)
	    				  {
	    					 for(q=0;q<x.length;q++)
	    					 {
	    						 x[q]=0;
	    					 }
	    					 m=T+1;
	    				  }
	    				  else
	    				  {
	    					  for(q=m;q<m+TW[i];q++)
		    				  {	    					  
		    				     for(p=n-L[i];p<n;p++)
		    					  {
		    						  if(aa[q][p]>m1)
		    						  {
		    							  m1=aa[q][p];
		    						  }
		    					  }	    					 
		    				  }
		    				  if(m1==1)  //在时间方向上存在重叠
	    					  {
		    					System.out.println("在时间方向上存在重叠"); 
	    						m++;
	    						n=0;
	    						n1=0;
	    						continue loop;
		    				  }
		    				  else       //存在相应的存储空间，则将船泊位 
		    				  {
		    					  System.out.println("存在相应的存储空间，则将船泊位 ");   
		    					  for(q=m;q<m+TW[i];q++)
			    				  {	    					  
			    				     for(p=n-L[i];p<n;p++)
			    					  {	    						 
			    							 aa[q][p]=1;		    						  
			    					  }	    					 
			    				  }
		    					  x[i]=m;
		    					  System.out.println("时间x["+i+"]="+x[i]);
		    					  x[i+A1]=n-L[i];
		    					  System.out.println("泊位x["+i+"]="+x[i+A1]);
		    					  continue loop1;
		    				  }
	    				  } 
	    			  }
	    		  }	 
	    	  }
	       }
	       
	       for(i=1;i<=A1;i++)    //求此时的最小值，minzfio初始为0，目标值为负数，所以用减
	       {
	    	   minzfio=minzfio-x[i];	    	   
	       }
	       for(i=0;i<x11.length;i++)
	       {
	    	   x11[i]=x[i]; 
	    	   xfio[i]=x[i];
	       }
	       v11[0]=minzfio;
	       vfio[0]=minzfio;
	       minz=minzfio-A1;
	       System.out.println("初始时minz="+minz);
	       if(minz==-A1)
	       {
	    	   minz=-Integer.MAX_VALUE;   
	       }
	        //v22初始化为负无穷
	      /*  for(i=0;i<v22.length;i++)
	        {
	        	v22[i]=-Integer.MAX_VALUE;
	        }*/
	      //原有abc保持不变，使用a1b1c1进行计算
	        for(i=0;i<a.length;i++)
	        {
	        	for(j=0;j<a[i].length;j++)
				{
					a1[i][j]=a[i][j];
				}
	        }							
			for(i=0;i<b.length;i++)
			{
				 b1[i]=b[i];
			}
				
			for(i=0;i<c.length;i++)
			    c1[i]=c[i];
	       x=s.SI(N, B, a1, b1, c1,v, x, t);
	       for(i=0;i<x.length;i++)//检查线性规划后的x值
	       {
	    	   if(x[i]>k)
	    	   {
	    		  k=x[i];
	    	   }
	       }
	       p=0;
	       if(k==0)//如果x值均为0，则线性规划无界或不可行
	    	   System.out.print("线性规划无界或不可行");
	       else if(k!=0&&v[0]<=minz)
	       {
	    	   return l6;
	       }
	       else  if(k!=0&&v[0]>minz)//线性规划可行
	       {
	    	  // v22[0]=v[0];
	    	   maxz=v[0];
	    	   i=1;	    	
	    	    while(i<=t2)//只检查t2个x值是否为整数
	    	      	{  
//**************************最开始时进行的分支定界**********************************8
	    	    	   x1[i]=Math.round(x[i]);//整数值
	    	    	   if(Math.abs(x1[i]-x[i])>0.001)//如果x[i]不是整数，进行分支限界
	    	    	   {
	    	    		   M1=x[i];	    	    		   
	    	    		   System.out.println("左x["+i+"]="+(Math.ceil(M1)-1));
		  	          		for(m=0;m<a.length;m++)
		        				for(n=0;n<a[m].length;n++)
		        					a1[m][n]=a[m][n];
		        			for(m=0;m<b.length;m++)
		        				b1[m]=b[m];
		        			for(m=0;m<c.length;m++)
		        			   c1[m]=c[m];
		        			
		        			 a1[t1+1][i]=1;//x[i]左侧区域A1
		         		     b1[t1+1]=(float)Math.ceil(M1)-1; 
		         		     al[t1+1][i]=1;//x[i]左侧区域Al
		         		     bl[t1+1]=(float)Math.ceil(M1)-1; 
		  	        			for(m=0;m<N.length;m++)
		  	        				N[m]=0;
		  	        			for(m=0;m<B.length;m++)
		  	        			   B[m]=0;
		  	          		    for(m=0;m<x.length;m++)
		  	         			   x[m]=0;
		  	          		       v[0]=0;	         
		  	          				  	          		    
		  	       			    x=s.SI(N, B, a1, b1, c1,v, x, t); 
		  	       			    //计算结束后将a1，b1的值设为0
		  	       			    for(m=0;m<a1.length;m++)
		  	       			    {
		  	       			    	for(n=0;n<a1[m].length;n++)
		  	       			    	{
		  	       			    	  a1[m][n]=0;
		  	       			    	}
		  	       			    		
		  	       			    }
		  	       			   for(m=0;m<b1.length;m++)
		  	       			   {
		  	       				   b1[m]=0;
		  	       			   }
		  	       			   for(m=0;m<x.length;m++)
		  	      			  {
		  	      				  x2[m]=x[m];  //将x结果保存到x2中	  	      				    	      				 
		  	      			  }
		  	       			 vl=v[0];     //v的结果用vl暂存		  	       			
		  	       			 System.out.println("vl="+vl);  
		  	       		 //*********判断线性规划是否可行*******
		  	       		    k=-1;
		  	       		    for(m=0;m<x2.length;m++)//k是左侧的最大值
	        		         {
	        		    	   if(x2[m]>k)
	        		    	   {
	        		    		  k=x2[m];
	        		    	   }
	        		         }
		  	       		 System.out.println("检查左侧值是否有非整数值");	 
		  	       		 if(k!=0&&vl>minz&&vl<=maxz)//如果值有效，则加入
	     				  { 
		  	       		      m=1;	  	        					   
	               			  while(m<=t2)
	               			  {
	               					  x1[m]=Math.round(x2[m]);//整数值
	                 		    	   if(x1[m]-x2[m]!=0)//x[i]不是整数
	                 		    	   {	                 		    		 
	                 		    		   System.out.println("左侧加入：x["+m+"]="+x2[m]);
	                 		    		  l1.add(p,m);
	               		    		      l2.add(p,x2[m]);               		    	
	                 		    		  l5.add(p,vl);
	                 		    		 // v22[p]=vl;
	                 		    	   
	                 		    	     for(m1=1;m1<=t2;m1++)
	                 		    	     {
	                 		    	    	 la.add(al[t1+1][m1]);	                 		    	    	
	                 		    	     }
	                 		    	     la.add(null);
	                 		    	     lb.add(bl[t1+1]);
	                 		    	     lb.add(null);
	                 		    	
	                 		    	    break;
	                 		    		} 
	                 		    	   else
	                 		    		   m++;	
	               				 
	               			  }
	               			if(m==t2+1)//线性规划结构全为整数并且>minz
	               			  {	
	               			      System.out.println("左侧结果全为整数");
	               					minz=vl;
	                  				 for(m=0;m<x11.length;m++)
	                  				  {
	                  					  x11[m]=x2[m];
	                  				  }
	                  				 for(m=0;m<=2*A1;m++)
	                  				 {
	                  					System.out.println("x["+m+"]="+x2[m]);
	                  				 }
	                  				 v11[0]=vl;  	                  			
	               				  // j++; //左侧全为整数且大于右侧，删除右侧分支
	               				 //  continue loop;
	               			} //线性规划结构全为整数并且>minz
	           			  } //新的线性规划可行  
		  	       		 else
		  	       		 {
		  	       			 System.out.println("左侧值无效");
		  	       		 }	     				 
		  	       		 		  	       		
		  	       		//***********右侧*******************
		  	       			System.out.println("右x[i]="+(Math.ceil(M1)));
		  	       		for(m=0;m<a.length;m++)
	        				for(n=0;n<a[m].length;n++)
	        					a1[m][n]=a[m][n];
	        			for(m=0;m<b.length;m++)
	        				b1[m]=b[m];
	        			for(m=0;m<c.length;m++)
	        			   c1[m]=c[m];
	        			            	
		             		      a1[t1+1][i]=-1;//x[i]左侧区域A1
		            		      b1[t1+1]=(float)-Math.ceil(M1); 
		            		      ar[t1+1][i]=-1;//x[i]左侧区域Ar
		            		      br[t1+1]=(float)-Math.ceil(M1); 
		  	            			for(m=0;m<N.length;m++)
		  	            				N[m]=0;
		  	            			for(m=0;m<B.length;m++)
		  	            			   B[m]=0;
		  	            			for(m=0;m<x.length;m++)
		  	                 			 x[m]=0;
		  	            			   v[0]=0;
		  	   	          					  	            	
		  	            		  x=s.SI(N, B, a1, b1, c1,v, x, t);
		  	            		 for(m=0;m<a1.length;m++)
			  	       			    {
			  	       			    	for(n=0;n<a1[m].length;n++)
			  	       			    	{
			  	       			    	  a1[m][n]=0;
			  	       			    	}
			  	       			    		
			  	       			    }
			  	       			   for(m=0;m<b1.length;m++)
			  	       			   {
			  	       				   b1[m]=0;
			  	       			   }
		  	            		  vr=v[0];
		  	            		  System.out.println("vr="+vr); 
		  	            		
		  	                //*********判断线性规划是否可行*******
		  	        			 k1=-1; 	        			 
		  	        	
		  	        			  for(m=0;m<x.length;m++)//k1是右侧的最大值
		  	       		         {
		  	       		    	   if(x[m]>k1)
		  	       		    	   {
		  	       		    		  k1=x[m]; 
		  	       		    	   }
		  	       		         }
		  	        			  	        		   	  	        				  
		  	        			 //查看x[i]值是否为整数
		  	        			 System.out.println("检查右侧是否有非整数值");
		  	        				  	        	
		  	        				 if(k1!=0&&vr>minz&&vr<=maxz)//右侧取值
		  	        				  { 	  	        					
		  	        					  m=1;
		  	                			  while(m<=t2)
		  	                			  {     				  
		  	                                 x1[m]=Math.round(x[m]);//整数值
		  	                   		    	   if(x1[m]-x[m]!=0)//x[i]不是整数
		  	                   		    	   {
		  	                   		    		   if(l1.size()==0)
		  	                   		    		   {
		  	                   		    			   p=0;
		  	                   		    		   }
		  	                   		    		   else
		  	                   		    		   {
		  	                   		    			 p++; 
		  	                   		    		   }		  	                   		    		  
		  	                   		    		 System.out.println("右侧加入：x["+m+"]="+x[m]);
		  	                   		    	    l1.add(p,m);
		                   		    		    l2.add(p,x[m]);	                   		    		
		  	               		    		    l5.add(p,vr);
		  	               		    		//    v22[p]=vr;
		  	               		    		
		                 		    	     for(m1=1;m1<=t2;m1++)
		                 		    	     {
		                 		    	    	 la.add(ar[t1+1][m1]);		                 		    	
		                 		    	     }
		                 		    	     la.add(null);
		                 		    	     lb.add(br[t1+1]);
		                 		    	     lb.add(null);		                 		    	 
		  	                   		    		 break;
		  	                   		    		}
		  	                   		    	   else
		  	                   		    	   {
		  	                   		    		m++; 
		  	                   		    	   }	  
		  	                			  }

		  	                			  if(m==t2+1)
		  	                			  {	   System.out.println("右侧全为整数");	                				 
		  	                					  minz=vr;
		  	                    				  for(m=0;m<x11.length;m++)
		  	                    				  {
		  	                    					  x11[m]=x[m];	  	                    				
		  	                    				  } 
		  	                    				 for(m=0;m<=2*A1;m++)
		  	                     				 {
		  	                     					System.out.println("x["+m+"]="+x[m]);
		  	                     				 }
		  	                    				  v11[0]=vr;	  	                					  	                				 
		  	                			  }  	 	                			 
		  	                				  
		  	        				  }
		  	        				else
		  	 	  	       		 {
		  	 	  	       			 System.out.println("右侧值无效");
		  	 	  	       		 }	  	        			
		  	        		 
	    	    		    break;
	    	    		}
	    	    	   else//如果x[i]是整数查看x[i+1]
	    	    		   i++;
	    	    	 }
	    	  if(i==t2+1)//如果x[]全是整数，则将x存入x11
	    	 	{  	    		 
	    			  minz=v[0];
	        		  for(j=0;j<x.length;j++)//并将此时的取值存储
	        			  {
	        				  x11[j]=x[j];
	        			  } 
	        		  v11[0]=v[0];	    		  	    		     
	    	 	}
 //***************************正式的分支定界过程***********************
	    	  else//如果x[]不全是整数，则进行分支限界
	    	  { 
	    		  if(l1.size()==0)
	    		  {
	    			  return l6;
	    		  }
	    		  else
	    		  {
	    			  j=0;  //每次计算的数	    		 
	  	    	    while(j<=p)//对列表中的值使用分支限界法
	  	        	  {  
	  	    		  
	  	    			  //取出原有的abc系数
	  	        		  i=l1.get(j);
	  	        		  M1=l2.get(j);	        	
	  	        		  vn=l5.get(j);	 
	  	        		  ia=la.indexOf(null);	
	  	        		  ib=lb.indexOf(null);	
	  	        		  for(m=1;m<=ib;m++)
	  	        		  {
	  	        			  for(n=1;n<=t2;n++)
	  	        			  {
	  	        				  a2[m][n]=la.get(0);
	  	        				  la.remove(0);
	  	        			  }
	  	        		  }
	  	        		  la.remove(null);
	  	        		  for(m=1;m<=ib;m++)
	  	        		  {
	  	        			  b2[m]=lb.get(0);
	  	        			  lb.remove(0);
	  	        		  }
	  	        		  lb.remove(null);	        		  
	  	        		  System.out.println("*******************x["+i+"]="+M1+"&&v="+vn);	
	  	        		  
	  	        		 /* System.out.println("取出");
	  	        		   for(m1=1;m1<=ib;m1++)
	  	          		       {
	  	          		    	   for(n1=1;n1<=t2;n1++)
	  	          		    	   {
	  	          		    		System.out.print(a2[m1][n1]+" ");
	  	          		    	   }
	  	          		    	   System.out.print("="+b2[m1]);
	  	          		    	  System.out.println();
	  	          		       }*/
	  	        		  //求出q的值，q是分支的层数，即新加入的基本变量的个数
	  	        		 // System.out.println("此时p="+p);
	  	        		  //System.out.println("此时ib="+ib);
	  	        		  //System.out.println("此时ia="+ia);
	  	        		//如果不符合条件，则直接跳过，不进行计算
	  	        		  if(vn>maxz||vn<minz) //?????????如果带等号会如何？？？？？？？
	  	        		  {
	  	        			  j++;
	  	        		  }
	  	        		   
	  	        		  else   //否则，进行分支定界的计算
	  	        		  {       			    
	  	  	        	  //***********左侧*******************	  	      			
	  	  	          		   System.out.println("左x["+i+"]="+(Math.ceil(M1)-1));
	  	  	          		for(m=0;m<a.length;m++)
	  	        				for(n=0;n<a[m].length;n++)
	  	        					a1[m][n]=a[m][n];
	  	        			for(m=0;m<b.length;m++)
	  	        				b1[m]=b[m];
	  	        			for(m=0;m<c.length;m++)
	  	        			   c1[m]=c[m];
	  	        			for(m=1;m<=ib;m++)
	  	        			{
	  	        			   for(n=1;n<=t2;n++)
	  	        			   {
	  	        				   a1[t1+m][n]=a2[m][n];
	  	        			   }
	  	        			   b1[t1+m]=b2[m];
	  	        			}
	  	        			 for(m1=0;m1<al.length;m1++)
	  	  	       			    {
	  	  	       			    	for(n1=0;n1<al[m1].length;n1++)
	  	  	       			    	{
	  	  	       			    	  al[m1][n1]=0;
	  	  	       			    	}
	  	  	       			    		
	  	  	       			    }
	  	  	       			   for(m1=0;m1<bl.length;m1++)
	  	  	       			   {
	  	  	       				   bl[m1]=0;
	  	  	       			   }
	  	        			 a1[t1+1+ib][i]=1;//x[i]左侧区域A1
	  	         		     b1[t1+1+ib]=(float)Math.ceil(M1)-1; 
	  	         		     al[t1+1+ib][i]=1;//x[i]左侧区域Al
	  	         		     bl[t1+1+ib]=(float)Math.ceil(M1)-1; 
	  	  	        			for(m=0;m<N.length;m++)
	  	  	        				N[m]=0;
	  	  	        			for(m=0;m<B.length;m++)
	  	  	        			   B[m]=0;
	  	  	          		    for(m=0;m<x.length;m++)
	  	  	         			   x[m]=0;
	  	  	          		       v[0]=0;  	          
	  	  	          			
	  	  	          		/*   for(m1=t1+1;m1<=t1+1+ib;m1++)
	  	  	          		       {
	  	  	          		    	   for(n1=1;n1<=t2;n1++)
	  	  	          		    	   {
	  	  	          		    		System.out.print(a1[m1][n1]+" ");
	  	  	          		    	   }
	  	  	          		    	   System.out.print("="+b1[m1]);
	  	  	          		    	  System.out.println();
	  	  	          		       }*/	  	          		
	  	  	       			    x=s.SI(N, B, a1, b1, c1,v, x, t); 
	  	  	       			    //计算结束后将a1，b1的值设为0
	  	  	       			    for(m=0;m<a1.length;m++)
	  	  	       			    {
	  	  	       			    	for(n=0;n<a1[m].length;n++)
	  	  	       			    	{
	  	  	       			    	  a1[m][n]=0;
	  	  	       			    	}
	  	  	       			    		
	  	  	       			    }
	  	  	       			   for(m=0;m<b1.length;m++)
	  	  	       			   {
	  	  	       				   b1[m]=0;
	  	  	       			   }
	  	  	       			   for(m=0;m<x.length;m++)
	  	  	      			  {
	  	  	      				  x2[m]=x[m];  //将x结果保存到x2中	  	      				    	      				 
	  	  	      			  }
	  	  	       			 vl=v[0];     //v的结果用vl暂存		  	       			
	  	  	       			 System.out.println("vl="+vl);  
	  	  	       			 
	  	  	       		System.out.println("右x[i]="+(Math.ceil(M1)));
	  	  	       		for(m=0;m<a.length;m++)
	          				for(n=0;n<a[m].length;n++)
	          					a1[m][n]=a[m][n];
	          			for(m=0;m<b.length;m++)
	          				b1[m]=b[m];
	          			for(m=0;m<c.length;m++)
	          			   c1[m]=c[m];
	          			for(m=1;m<=ib;m++)
	          			{
	          			   for(n=1;n<=t2;n++)
	          			   {
	          				   a1[t1+m][n]=a2[m][n];
	          			   }
	          			   b1[t1+m]=b2[m];
	          			}  	
	          			 for(m1=0;m1<ar.length;m1++)
	  	       			    {
	  	       			    	for(n1=0;n1<ar[m1].length;n1++)
	  	       			    	{
	  	       			    	  ar[m1][n1]=0;
	  	       			    	}
	  	       			    		
	  	       			    }
	  	       			   for(m1=0;m1<br.length;m1++)
	  	       			   {
	  	       				   br[m1]=0;
	  	       			   }
	  	             		      a1[t1+1+ib][i]=-1;//x[i]左侧区域A1
	  	            		      b1[t1+1+ib]=(float)-Math.ceil(M1); 
	  	            		      ar[t1+1+ib][i]=-1;//x[i]左侧区域Ar
	  	            		      br[t1+1+ib]=(float)-Math.ceil(M1); 
	  	  	            			for(m=0;m<N.length;m++)
	  	  	            				N[m]=0;
	  	  	            			for(m=0;m<B.length;m++)
	  	  	            			   B[m]=0;
	  	  	            			for(m=0;m<x.length;m++)
	  	  	                 			 x[m]=0;
	  	  	            			   v[0]=0;
	  	  	   	          			
	  	  	            			/* for(m=t1+1;m<=t1+ib+1;m++)
	  		  	          		       {
	  		  	          		    	   for(n=1;n<=t2;n++)
	  		  	          		    	   {
	  		  	          		    		System.out.print(a1[m][n]+" ");
	  		  	          		    	   }
	  		  	          		    	   System.out.print("="+b1[m]);
	  		  	          		    	  System.out.println();
	  		  	          		       }*/
	  	  	            		  x=s.SI(N, B, a1, b1, c1,v, x, t);	  	            	
	  	  	            		 for(m=0;m<a1.length;m++)
	  		  	       			    {
	  		  	       			    	for(n=0;n<a1[m].length;n++)
	  		  	       			    	{
	  		  	       			    	  a1[m][n]=0;
	  		  	       			    	}
	  		  	       			    		
	  		  	       			    }
	  		  	       			   for(m=0;m<b1.length;m++)
	  		  	       			   {
	  		  	       				   b1[m]=0;
	  		  	       			   }
	  	  	            		  vr=v[0];
	  	  	            		  System.out.println("vr="+vr); 
	  	  	       			 
	  	  	       		 //*********判断左侧线性规划是否可行*******
	  	  	       		    k=-1;
	  	  	       		    for(m=0;m<x2.length;m++)//k是左侧的最大值
	          		         {
	          		    	   if(x2[m]>k)
	          		    	   {
	          		    		  k=x2[m];
	          		    	   }
	          		         }
	  	  	       		  k1=-1; 	        			 
	  		  	        	
	  	        			  for(m=0;m<x.length;m++)//k1是右侧的最大值
	  	       		         {
	  	       		    	   if(x[m]>k1)
	  	       		    	   {
	  	       		    		  k1=x[m]; 
	  	       		    	   }
	  	       		         }
	  	        			  
	  	        			  if(vl>=vr)
	  	        			  {
	  	        				  System.out.println("检查左侧值是否有非整数值");	 
	  	    	  	       		  if(k!=0&&vl>minz&&vl<=maxz)//如果值有效，则加入
	  	         				  { 	    	  	       			  
	  	         					   m=1;	  	        					   
	  	                   			  while(m<=t2)
	  	                   			  {               				  
	  	                   					  x1[m]=Math.round(x2[m]);//整数值
	  	                     		    	   if(x1[m]-x2[m]!=0)//x[i]不是整数
	  	                     		    	   {
	  	                     		    		   if(m==i)
	  	                     		    		   {
	  	                     		    			   break;
	  	                     		    		   }
	  	                     		    		   else
	  	                     		    		   {
	  	                     		    			  p++;
	  		                     		    		   System.out.println("左侧加入：x["+m+"]="+x2[m]);
	  		                     		    		  l1.add(p,m);
	  		                   		    		      l2.add(p,x2[m]);               		    	
	  		                     		    		  l5.add(p,vl);
	  		                     		    	     for(m1=1;m1<=ib;m1++)                 		    		 
	  		                     		    	     {
	  		                     		    	    	 for(n1=1;n1<=t2;n1++)
	  		                     		    	    	 {
	  		                     		    	    		 la.add(a2[m1][n1]);
	  		                     		    	    	 }
	  		                     		    	    	 lb.add(b2[m1]);
	  		                     		    	      }              		    	     
	  		                     		    	    for(m1=1;m1<=t2;m1++)
	  		                     		    	     {
	  		                     		    	    	 la.add(al[t1+1+ib][m1]);
	  		                     		    	    	
	  		                     		    	     }
	  		                     		    	     la.add(null);
	  		                     		    	     lb.add(bl[t1+1+ib]);
	  		                     		    	     lb.add(null);                 		    		  	          		     
	  		                     		    	    break;	         
	  	                     		    		   }	                     		    			              		    	                     		    		  
	  	                     		    		} 
	  	                     		    	   else
	  	                     		    	   {
	  	                     		    		   m++;	
	  	                     		    	   }                		    		
	  	                   			  }               			  
	  	                   			  
	  	                   			if(m==t2+1)//线性规划结构全为整数并且>minz
	  	                   			  {	
	  	                   			      System.out.println("左侧结果全为整数");
	  	                   					minz=vl;
	  	                      				 for(m=0;m<x11.length;m++)
	  	                      				  {
	  	                      					  x11[m]=x2[m];
	  	                      				  }
	  	                      				 for(m=0;m<=2*A1;m++)
	  	                      				 {
	  	                      					System.out.println("x["+m+"]="+x2[m]);
	  	                      				 }
	  	                      				 v11[0]=vl;  	                  			
	  	                   				  // j++; //左侧全为整数且大于右侧，删除右侧分支
	  	                   				 //  continue loop;
	  	                   			} //线性规划结构全为整数并且>minz              				       	    
	  	               			  } //新的线性规划可行  
	  	    	  	       		 else
	  	    	  	       		 {
	  	    	  	       			 System.out.println("左侧值无效");
	  	    	  	       		 }  	       		 
	  	    	  	       	     for(m1=0;m1<v22.length;m1++)
	  	    	  	       			 v22[m1]=-Integer.MAX_VALUE;
	  	    	  	       		     m1=j+1;
	  	    	  	       		 n1=0;
	  	    	  	       		 while(m1<=p)
	  	    	  	       		 {
	  	    	  	       			 v22[n1]=l5.get(m1);
	  	    	  	       		     System.out.print(v22[n1]+" ");
	  	    	  	       			 m1++;
	  	    	  	       			 n1++;
	  	    	  	       		 }
	  	       				     maxz=s.max(v22);
	  	       				      System.out.println();
	  	         				  System.out.println("maxz="+maxz); 	  	       		
	  	    	  	            		
	  	    	  	                //*********判断右侧线性规划是否可行*******
	  	    	  	        			
	  	    	  	        			  	        		   	  	        				  
	  	    	  	        			 //查看x[i]值是否为整数
	  	    	  	        			 System.out.println("检查右侧是否有非整数值");
	  	    	  	        				  	        	
	  	    	  	        				 if(k1!=0&&vr>minz&&vr<=maxz)//右侧取值
	  	    	  	        				  { 	  	        					
	  	    	  	        					  m=1;
	  	    	  	                			  while(m<=t2)
	  	    	  	                			  {  
	  	    	  	                					x1[m]=Math.round(x[m]);//整数值
	  	    	  	                   		    	   if(x1[m]-x[m]!=0)//x[i]不是整数
	  	    	  	                   		    	   {
	  	    	  	                   		    		 if(m==i)
	  	  	                     		    		     {
	  	  	                     		    			   break;
	  	  	                     		    		      }
	  	    	  	                   		    		 else
	  	    	  	                   		    		 {
	  	    	  	                   		    		 p++;
	  	    	  	                   		    		 System.out.println("右侧加入：x["+m+"]="+x[m]);
	  	    	  	                   		    	    l1.add(p,m);
	  	    	                   		    		    l2.add(p,x[m]);	                   		    		
	  	    	  	               		    		   l5.add(p,vr);
	  	    	  	               		    //		    v22[p]=vr;
	  	    	  	               		    		 for(m1=1;m1<=ib;m1++)                 		    		 
	  	    	                 		    	     {
	  	    	                 		    	    	 for(n1=1;n1<=t2;n1++)
	  	    	                 		    	    	 {
	  	    	                 		    	    		 la.add(a2[m1][n1]);
	  	    	                 		    	    	 }
	  	    	                 		    	    	 lb.add(b2[m1]);
	  	    	                 		    	      }
	  	    	  	               		       
	  	    	                 		    	     for(m1=1;m1<=t2;m1++)
	  	    	                 		    	     {
	  	    	                 		    	    	 la.add(ar[t1+1+ib][m1]);
	  	    	                 		    	     }
	  	    	                 		    	     la.add(null);
	  	    	                 		    	     lb.add(br[t1+1+ib]);
	  	    	                 		    	     lb.add(null);	      	  	          		  
	  	    	  	                   		    		 break;	     
	  	    	  	                   		    		 }	    	  	                   		    				  	                   		    			    	  	                   		    		  
	  	    	  	                   		    		}
	  	    	  	                   		    	   else
	  	    	  	                   		    	   {
	  	    	  	                   		    		m++; 
	  	    	  	                   		    	   }	  
	  	    	  	                			  }  	                			  
	  	    	  	                	
	  	    	  	                			  if(m==t2+1)
	  	    	  	                			  {	   System.out.println("右侧全为整数");	                				 
	  	    	  	                					  minz=vr;
	  	    	  	                    				  for(m=0;m<x11.length;m++)
	  	    	  	                    				  {
	  	    	  	                    					  x11[m]=x[m];	  	                    				
	  	    	  	                    				  } 
	  	    	  	                    				 for(m=0;m<=2*A1;m++)
	  	    	  	                     				 {
	  	    	  	                     					System.out.println("x["+m+"]="+x[m]);
	  	    	  	                     				 }
	  	    	  	                    				  v11[0]=vr;	  	                					  	                				 
	  	    	  	                			  }  	 	                			 
	  	    	  	                			 	  
	  	    	  	        				  }
	  	    	  	        				else
	  	    	  	 	  	       		 {
	  	    	  	 	  	       			 System.out.println("右侧值无效");
	  	    	  	 	  	       		 }
	  	    	  	        			  	        				
	  	    	  	        			 //计算结束后将值设为0  	        			
	  	                        			// v22[j]=-Integer.MAX_VALUE;
	  	                        		 //求maxz的取值
	  	    	  	        				 m1=j+1;
	  	      		  	 	  	       		 n1=0;
	  	      		  	 	  	       		 while(m1<=p)
	  	      		  	 	  	       		 {
	  	      		  	 	  	       			 v22[n1]=l5.get(m1);
	  	      		  	 	  	            	System.out.print(v22[n1]+" ");
	  	      		  	 	  	       			 m1++;
	  	      		  	 	  	       			 n1++;
	  	      		  	 	  	       		 }
	  	      		  	      				 maxz=s.max(v22);	
	  	      		  	      			 System.out.println();
	  	                         	      System.out.println("maxz="+maxz);	  	   
	  	        			  }
	  	        			  else if(vl<vr)
	  	        			  {
	  	        				  //*********判断右侧线性规划是否可行*******  	        			
	  	   	  	        				 
	   	  	        			 System.out.println("检查右侧是否有非整数值");
	   	  	        				  	        	
	   	  	        				 if(k1!=0&&vr>minz&&vr<=maxz)//右侧取值
	   	  	        				  { 	  	        					
	   	  	        					  m=1;
	   	  	                			  while(m<=t2)
	   	  	                			  {  
	   	  	                					x1[m]=Math.round(x[m]);//整数值
	   	  	                   		    	   if(x1[m]-x[m]!=0)//x[i]不是整数
	   	  	                   		    	   {
	   	  	                   		    	     if(m==i)
	                     		    		         {
	                     		    			        break;
	                     		    		         }
	   	  	                   		    	     else
	   	  	                   		    	     {
	   	  	                   		    	   p++;
	  	  	                   		    		 System.out.println("右侧加入：x["+m+"]="+x[m]);
	  	  	                   		    	    l1.add(p,m);
	  	                   		    		    l2.add(p,x[m]);	                   		    		
	  	  	               		    		   l5.add(p,vr);
	  	  	               		    		 for(m1=1;m1<=ib;m1++)                 		    		 
	  	                 		    	     {
	  	                 		    	    	 for(n1=1;n1<=t2;n1++)
	  	                 		    	    	 {
	  	                 		    	    		 la.add(a2[m1][n1]);
	  	                 		    	    	 }
	  	                 		    	    	 lb.add(b2[m1]);
	  	                 		    	      }
	  	  	               		       
	  	                 		    	     for(m1=1;m1<=t2;m1++)
	  	                 		    	     {
	  	                 		    	    	 la.add(ar[t1+1+ib][m1]);
	  	                 		    	     }
	  	                 		    	     la.add(null);
	  	                 		    	     lb.add(br[t1+1+ib]);
	  	                 		    	     lb.add(null);	      	  	          		  
	  	  	                   		    		 break;  
	   	  	                   		    	     }
	   	  	                   		    		 
	   	  	                   		    	 }	  	                   		    		  
	   	  	                   		    	
	   	  	                   		    	   else
	   	  	                   		    	   {
	   	  	                   		    		m++; 
	   	  	                   		    	   }	  
	   	  	                			  }  	                			  
	   	  	                	
	   	  	                			  if(m==t2+1)
	   	  	                			  {	   System.out.println("右侧全为整数");	                				 
	   	  	                					  minz=vr;
	   	  	                    				  for(m=0;m<x11.length;m++)
	   	  	                    				  {
	   	  	                    					  x11[m]=x[m];	  	                    				
	   	  	                    				  } 
	   	  	                    				 for(m=0;m<=2*A1;m++)
	   	  	                     				 {
	   	  	                     					System.out.println("x["+m+"]="+x[m]);
	   	  	                     				 }
	   	  	                    				  v11[0]=vr;	  	                					  	                				 
	   	  	                			  }  	 	                			 
	   	  	                			 	  
	   	  	        				  }
	   	  	        				else
	   	  	 	  	       		 {
	   	  	 	  	       			 System.out.println("右侧值无效");
	   	  	 	  	       		 }
	   	  	        			  	        				
	   	  	        			 //计算结束后将值设为0  	        			
	                       			// v22[j]=-Integer.MAX_VALUE;
	                       		 //求maxz的取值
	   	  	        				 m1=j+1;
	     		  	 	  	       		 n1=0;
	     		  	 	  	       		 while(m1<=p)
	     		  	 	  	       		 {
	     		  	 	  	       			 v22[n1]=l5.get(m1);
	     		  	 	  	                 System.out.print(v22[n1]+" ");
	     		  	 	  	       			 m1++;
	     		  	 	  	       			 n1++;
	     		  	 	  	       		 }
	     		  	      				 maxz=s.max(v22);
	     		  	      			  System.out.println();
	                        	      System.out.println("maxz="+maxz);	  	
	  	        				  System.out.println("检查左侧值是否有非整数值");	 
	  	    	  	       		  if(k!=0&&vl>minz&&vl<=maxz)//如果值有效，则加入
	  	         				  {     					
	  	         					   m=1;	  	        					   
	  	                   			  while(m<=t2)
	  	                   			  {               				  
	  	                   					  x1[m]=Math.round(x2[m]);//整数值
	  	                     		    	   if(x1[m]-x2[m]!=0)//x[i]不是整数
	  	                     		    	   {	
	  	                     		    		  if(m==i)
	  	                     		    		   {
	  	                     		    			   break;
	  	                     		    		   }
	  	                     		    		  else
	  	                     		    		  {
	  	                     		    			 p++;
	  		                     		    		   System.out.println("左侧加入：x["+m+"]="+x2[m]);
	  		                     		    		  l1.add(p,m);
	  		                   		    		      l2.add(p,x2[m]);               		    	
	  		                     		    		  l5.add(p,vl);
	  		                     		   // 		  v22[p]=vl;
	  		                     		    	     for(m1=1;m1<=ib;m1++)                 		    		 
	  		                     		    	     {
	  		                     		    	    	 for(n1=1;n1<=t2;n1++)
	  		                     		    	    	 {
	  		                     		    	    		 la.add(a2[m1][n1]);
	  		                     		    	    	 }
	  		                     		    	    	 lb.add(b2[m1]);
	  		                     		    	      }              		    	     
	  		                     		    	    for(m1=1;m1<=t2;m1++)
	  		                     		    	     {
	  		                     		    	    	 la.add(al[t1+1+ib][m1]);
	  		                     		    	    	
	  		                     		    	     }
	  		                     		    	     la.add(null);
	  		                     		    	     lb.add(bl[t1+1+ib]);
	  		                     		    	     lb.add(null);                 		    		  	          		     
	  		                     		    	    break;              
	  	                     		    		  }                     		    			           		    		
	  	                     		    		  
	  	                     		    		} 
	  	                     		    	   else
	  	                     		    	   {
	  	                     		    		   m++;	
	  	                     		    	   }                		    		
	  	                   			  }               			  
	  	                   			  
	  	                   			if(m==t2+1)//线性规划结构全为整数并且>minz
	  	                   			  {	
	  	                   			      System.out.println("左侧结果全为整数");
	  	                   					minz=vl;
	  	                      				 for(m=0;m<x11.length;m++)
	  	                      				  {
	  	                      					  x11[m]=x2[m];
	  	                      				  }
	  	                      				 for(m=0;m<=2*A1;m++)
	  	                      				 {
	  	                      					System.out.println("x["+m+"]="+x2[m]);
	  	                      				 }
	  	                      				 v11[0]=vl;  	                  			
	  	                   				  // j++; //左侧全为整数且大于右侧，删除右侧分支
	  	                   				 //  continue loop;
	  	                   			} //线性规划结构全为整数并且>minz              				       	    
	  	               			  } //新的线性规划可行  
	  	    	  	       		 else
	  	    	  	       		 {
	  	    	  	       			 System.out.println("左侧值无效");
	  	    	  	       		 }  	       		 
	  	    	  	       	     for(m1=0;m1<v22.length;m1++)
	  	    	  	       			 v22[m1]=-Integer.MAX_VALUE;
	  	    	  	       		     m1=j+1;
	  	    	  	       		 n1=0;
	  	    	  	       		 while(m1<=p)
	  	    	  	       		 {
	  	    	  	       			 v22[n1]=l5.get(m1);
	  	    	  	       		     System.out.print(v22[n1]+" ");
	  	    	  	       			 m1++;
	  	    	  	       			 n1++;
	  	    	  	       		 }
	  	       				     maxz=s.max(v22);	
	  	       				      System.out.println();
	  	         				  System.out.println("maxz="+maxz); 	       		      
	  	        			  }  	       		        				
	  	  	        			
	                       	     System.out.println("minz="+minz);	  	
	  	  	        				 j++;	  	        				 
	  	  	        				System.gc();//垃圾回收	

	  	        		  }
	  	        		 
	  	        	  }//对列表中的值使用分支限界法  
	    		  }
	    		 
	    	  
	    	  }//如果x[]不全是整数，则进行分支限界
	       
	       }//线性规划可行
	       if(minz==-Integer.MAX_VALUE)
	       {
	    	   v11[0]=-Integer.MAX_VALUE;
	       }
	       if(minz<minzfio)
	       {
	    	   v11[0]=vfio[0];
	    	   for(i=0;i<x11.length;i++)
	    	   {
	    		 x11[i]=xfio[i];  
	    	   }
	       }
	return l6;     
}
}
class Compute
{
	int i,j,t,m,k1,k2;
	int L1,L2;
	int L0;
	float v[]=new float[1];
	float vl;
	int N[]; 
	int  B[]; 
	float x[];  
	float xl[];  
	float b[]; 
	float a[][]; 
	float c[]; 
	
/*	
 *  plan根据船舶信息生成线性规划方程，返回方程的系数
 */
	public ArrayList<float[]> plan(int A,int T,int M,int CRS,int TS[],int W[],int L[],int C[],int TW[],int PR[])
	{
		
		ArrayList<int[]> l1;
		ArrayList<float[]> l2;
		ArrayList<ArrayList> l3;

		 L2=2*A+A*A*2; //非基本变量的个数
		 //L1=2*A*(A-1)+6*A+3*A*(A-1)/2+A*A*2;
		 L1=4*A+A*A*2+4*A*A+3*A*(A-1)/2;  //非基本变量加基本变量的个数
		 L0=L1+100;
	     N=new int[L0]; 
		 B=new int[L0]; 
		 x=new float[L0];  
		 xl=new float[L0];  
		 b=new float[L0]; 
		 a=new float[L0][L0]; 
		 c=new float[L0]; 
		 vl=Integer.MIN_VALUE;
		 System.out.println("基本变量个数："+(L1-L2));
		 System.out.println("非基本变量个数："+L2);
		 ArrayList<float[]> l4=new ArrayList<float[]>();
		 l4.add(x);
		 l4.add(v);

			//*******设置a.b.c的值*初始化均为0***********
		     for(i=0;i<a.length;i++)
				 for(j=0;j<a[i].length;j++)
					 a[i][j]=0;
			 for(i=0;i<b.length;i++)
				 b[i]=0;
			 for(i=0;i<c.length;i++)
				 c[i]=0;
			 
			 //目标函数  在港时间最短，即开始时间之和最小
			 for(t=1;t<=A;t++)
				{
					c[t]=-1;
				}
			 
			//任意两艘船不交叠
			//System.out.println("A="+A);
	     	 //System.out.println("任意两艘船不交叠");	
			   k1=L2+1;
				for(i=1;i<=A;i++) 
				{
					for(j=1;j<=A;j++)
					{
						if(j!=i)
						{
							a[k1][j]=-1;
							a[k1][i]=1;
							a[k1][A*2+(i-1)*A+j]=T;
							b[k1]=T-TW[i];
							k1++;
							
							a[k1][A+j]=-1;
							a[k1][A+i]=1;
							a[k1][2*A+A*A+(i-1)*A+j]=M;
							b[k1]=M-L[i];
							k1++;
							
							a[k1][2*A+(i-1)*A+j]=1;
							b[k1]=1;
							k1++;
							
							a[k1][2*A+A*A+(i-1)*A+j]=1;
							b[k1]=1;
							k1++;
						}
						else if(i==j)
						{
							a[k1][2*A+(i-1)*A+j]=1;
							b[k1]=0;
							k1++;
							a[k1][2*A+A*A+(i-1)*A+j]=1;
							b[k1]=0;
							k1++;
						}
					}
   
				}
				for(i=1;i<=A;i++)
				{
					for(j=i+1;j<=A;j++)
					{
						a[k1][2*A+(i-1)*A+j]=1;
						a[k1][2*A+(j-1)*A+i]=1;
						b[k1]=1;
						k1++;
						
						a[k1][2*A+A*A+(i-1)*A+j]=1;
						a[k1][2*A+A*A+(j-1)*A+i]=1;
						b[k1]=1;
						k1++;
						
						a[k1][2*A+(i-1)*A+j]=-1;
						a[k1][2*A+(j-1)*A+i]=-1;
						a[k1][2*A+A*A+(i-1)*A+j]=-1;
						a[k1][2*A+A*A+(j-1)*A+i]=-1;
						b[k1]=-1;
						k1++;
		               
						
					}
				}//任意两艘船不交叠
				
				
				
				//泊位时间和位置约束
				//System.out.println("泊位时间和位置约束");		
			    for(i=1;i<=A;i++)
			    {
			    	a[k1][i]=-1; //泊位时间大于到达时间
			    	b[k1]=-TS[i];
			    	k1++;
			    	a[k1][A+i]=-1;//空间大于1
			    	b[k1]=-1;
			    	k1++;
			    	a[k1][i]=1;//结束时间小于等于T
			    	b[k1]=T-TW[i]+1;
			    	k1++;
			    	a[k1][A+i]=1;//结束位置小于等于M
			    	b[k1]=M-L[i]+1;
			    	k1++;
			    }
			    
			    
			    //桥吊约束
			    /*   for(i=1;i<=A;i++)
			    {
			    	for(j=i+1;j<=A;j++)
			    	{
			    		if(C[i]+C[j]>CRS)
			    		{
			    			a[k1][i]=1;
			    			a[k1][j]=-1;
			    			b[k1]=-L[i]-1;
			    			k1++;
			    		}
			    	}
			    }*/
				System.out.println("开始进行泊位分配：");	
				l1=new ArrayList<int[]>();
				 l2=new ArrayList<float[]>();
				 l3=new ArrayList<ArrayList>();				
				l1.add(N);
				l1.add(B);
				l2.add(x);
				l3.add(l1);
				l3.add(l2);	
			    SIM s=new SIM();
		   
			    l4=s.Branch(A,T,M,TS,L,TW,N,B,a, b, c,v,x, L1, L2);

					
				    	//任意时刻分配桥吊总数限制

				  /*  	for(i=1;i<=A;i++)
				    	{
				    		for(j=(int)x[i];j<x[i]+TW[i];j++)
				    		{
				    			cr[i][j]=1;
				    		}
				    	}
				    	for(i=1;i<=T;i++)
				    	{
				    		t=0;
				    		for(j=1;j<=A;j++)
				    			t=t+cr[j][i]*C[j];
				    		
				    	}*/
				   
		return l4;
	}//plan
}

class Init extends JFrame implements ActionListener
{
	
	JLabel jl1,jl2,jl4,jl6,jl7;
	JTextField jt1,jt2;
	JLabel jl3[],jl5[];
	JTextField jt3[],jt5[];
	JButton jb1,jb2,jb3,jb4,jb11,jb12,jb22,jb33,jb44,jb55,jb66;
	JLabel jl10,jl20,jl40,jl60;
	JTextField jt10,jt20;
	JLabel jl30[],jl50[];
	JTextField jt30[],jt50[];
	JButton jb10,jb20,jb30,jb40,jb110,jb120;
	float a[][];
	float c[];
	float b[];
	int L1,L2;
	int N[];
	int B[];
	float v[]=new float[1];
	float x[];
	int l,e;
	int i,j,k1,k2,k,k0,m,n;
	int A,M,T,CRS;
	int TS[],W[],L[],C[],TW[],PR[];
	int s1,s2,s3,s4,s5,s6;
	int t1=0;
	int t=0;
	int L0;
	int d1,d2;
	float kx=-1;
	//ArrayList<int[]> l1;
	ArrayList<float[]> l2;
	//ArrayList<ArrayList> l3;
	JPanel jp=new JPanel();
	JPanel jp11=new JPanel();
	JPanel jp1=new JPanel();
	JPanel jp2=new JPanel();
	JPanel jp3=new JPanel();
	JPanel jp4=new JPanel();
	JPanel jp5=new JPanel();
	JPanel jp6=new JPanel();
	JPanel jp7=new JPanel();
	JPanel jp8=new JPanel();
	JPanel jp10=new JPanel();
	JPanel jp20=new JPanel();
	JPanel jp30=new JPanel();
	JPanel jp40=new JPanel();
	JPanel jp50=new JPanel();
	JPanel jp60=new JPanel();
	JTextArea jt=new JTextArea(30,30);
	JTextArea jt0=new JTextArea(30,30);
	JTextArea jt11=new JTextArea(30,30);
	JTextArea jt22=new JTextArea(30,50);
	FileDialog fd;
	Init()
	{
		jp11.setLayout(new FlowLayout());
	    jb11=new JButton("打开");
		jp11.add(jb11);
		add(jp11);
		jb11.addActionListener(this);
		
	}
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
	
	{ 
		if(e.getSource()==jb11||e.getSource()==jb22)
		{
			if(e.getSource()==jb22)
			{
			 jt0.setText(null);
			 jb22.setVisible(false);
			 jb33.setVisible(false);
			}
				
			fd=new FileDialog(this,"打开文件",FileDialog.LOAD);	
			fd.setDirectory(".");
			fd.show();
			try
			{
				File myfile=new File(fd.getDirectory(),fd.getFile());
				FileInputStream read=new FileInputStream(myfile);
				InputStreamReader in = new InputStreamReader(read);
				jt.append("\n");
				int v=0;
			    int k,m,k0,m0; 
				int a0[]=new int [1000];
				int t0[]=new int [1000];//整数部分
				int t10[]=new int [1000];//小数部分
				int b0;
				int h0[]=new int[1000];
				/*先将文件中的数据完全存入h0中，再将其中的空格回车删除，特别
				 * 是把一个个的数字根据实际情况生成十位数或百位数存入a0中*/
				while((v=in.read())!=-1)
				{
					
						 b0=v-48;
						 h0[i]=b0; 
						 i++;
						
				}
				for(j=i;j<h0.length;j++)
					h0[j]=-1;
			    in.close();
				i=0;
				j=0;
				k=0;
				k1=0;
				k2=0;
		        loop:while(i<h0.length)
		        {
		        	if(h0[i]>=0)
		        	{
		        		k=i;
		        		k0=i;
		        		for(;h0[i]>=0;i++)
		        		{
		        			t0[k]=h0[i];
		        			k++;
		        		}
		        		
		        			m=k-k0-1;
			        		for(k=k-1;k>=k0;k--)
			    			{
			    				a0[j]=a0[j]+t0[k]*(int)Math.pow(10,m-k+k0);
			    				
			    			}
			        		if(k0-1>=0&&h0[k0-1]==-3)
			        		{
			        			a0[j]=-a0[j];
			        		}
			        		j++;
			               continue loop;
		        		}
		        	
		        	else 
		        	{
		        		i++;
		        		continue loop;
		        	}
		        }
		    
				//**********************************************************
				A=(int)a0[0];
			    M=(int)a0[1];
			    T=(int)a0[2];
			    CRS=(int)a0[3];
			    TS=new int[A+1];
			    W=new int[A+1];
			   L=new int[A+1];
			    C=new int[A+1];
			   PR=new int[A+1];
			   TW=new int[A+1];
			    for(i=1;i<=A;i++)
			    	TS[i]=(int)a0[3+i];
			    for(i=1;i<=A;i++)
			    	W[i]=(int)a0[3+A+i];
			    for(i=1;i<=A;i++)
			    	L[i]=(int)a0[3+2*A+i];
			    for(i=1;i<=A;i++)
			    	C[i]=(int)a0[3+3*A+i];
			    for(i=1;i<=A;i++)
			    	PR[i]=(int)a0[3+4*A+i];
			    for(i=1;i<=A;i++)
			    	TW[i]=W[i]/C[i];
				
			
			   
			} 
			catch(IOException ioe)
			{
				ioe.printStackTrace();   
			} 
			 jb22=new JButton("上一步");
			 jb33=new JButton("显示结果");
			 jp6.setVisible(true);
			 jp6.add(jt0,BorderLayout.CENTER);
			 jp7.add(jb22,FlowLayout.LEFT);
			 jp7.add(jb33,FlowLayout.CENTER);
			 jp6.add(jp7,BorderLayout.SOUTH);
			 jt0.append("船的个数："+A+"\n");
			 jt0.append("泊位的个数："+M+"\n");
			 jt0.append("时间数："+T+"\n");
			 jt0.append("桥吊的个数："+CRS+"\n");
			 jt0.append("\n"+"到达时刻："+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(TS[i]+"  ");
				}
			 jt0.append("\n"+"船的作业量："+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(W[i]+"  ");
				}
			 jt0.append("\n"+"船长："+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(L[i]+"  ");
				}
			 jt0.append("\n"+"计划工作路数："+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(C[i]+"  ");
				}
			 jt0.append("\n"+"偏好："+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(PR[i]+"  ");
				}
			 jt0.append("\n"+"工作时间："+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(TW[i]+"  ");
				}

			 jb22.addActionListener(this);
			 jb33.addActionListener(this);
			 jp11.setVisible(false);
			 add(jp6);
		}
		//***************************************
		
		else if(e.getSource()==jb33)
		{
			jp6.setVisible(false);
			 L2=2*A+A*A*2;
			 x=new float[L0];  
		     Compute s=new Compute();
		     l2=new ArrayList<float[]> ();
		     l2=s.plan(A,T,M,CRS,TS,W,L, C,TW,PR);
		     x=l2.get(0);
		     v=l2.get(1);	
		     jb44=new JButton("继续");
		     jp8.add(jb44,FlowLayout.LEFT);
		     jt11.append("\n"+A+"条船的泊位结果如下："+"\n");
		      
			   if(v[0]==-Integer.MAX_VALUE)
				   jt11.append("无可行整数解");
			   else
			   {
				   jt11.append("start time:"+"\n");
				   for(i=1;i<=A;i++)
				 	{					    
				 		jt11.append("x["+i+"]="+x[i]+"\n");
				 	}
				   jt11.append("Berth:"+"\n");
				   for(i=A+1;i<=2*A;i++)
				 	{					   
				 		jt11.append("x["+i+"]="+x[i]+"\n");
				 	}
				 	jt11.append("max z="+v[0]); 
			   }
				 	
			   
		  jp50.add(jt11,BorderLayout.CENTER);
		  jp50.add(jp8,BorderLayout.SOUTH);		  
		  jb44.addActionListener(this);
		 	add(jp50);
		}
		else if(e.getSource()==jb44)
		{
			jp50.setVisible(false);
			 jb55=new JButton("直观图");
			 jb66=new JButton("退出");
			jp60.add(jb55);
			jp60.add(jb66);
			  jb55.addActionListener(this);
			  jb66.addActionListener(this);
			add(jp60);
			jp60.setVisible(true);
		}	
		else if(e.getSource()==jb55)
		{
			repaint();
		}
		else if(e.getSource()==jb66) //关闭窗口
		{
			System.exit(0);
		}
		}
	public void paint(Graphics g)
	{
		g.drawLine(50,50,50,650);
		g.drawLine(50,50,40,60);//箭头
		g.drawLine(50,50,60,60);
		g.drawLine(50,650,1260,650);
		g.drawLine(1260,650,1250,640);//箭头
		g.drawLine(1260,650,1250,660);
		try
		{
			d1=1200/M;		
			d2=480/T;
		}catch(ArithmeticException e)
		{			
		}
		Color c=new Color(255,255,0);//黄色-坐标暗格
		int da=0,db=0,dc=0,dd=0;
		for(i=1;i<=M;i++)   //竖线
		{
			g.setColor(c);
			g.drawLine(50+i*d1, 650,50+i*d1,50);
			g.setColor(Color.black);    //黑色坐标
			g.drawString(""+i, 30+i*d1, 670);
		}
		for(i=1;i<=T;i++)    //横线
		{
			g.setColor(c);
			g.drawLine(50,650-i*d2,1250,650-i*d2);
			g.setColor(Color.black);   //黑色坐标
			g.drawString(""+i, 30, 665-i*d2);
		}
	    	g.setColor(Color.black);			
			g.drawString("泊位",1250,680);
			g.drawString("时间",20,100);
		
		//da，db绘制矩形的左上角坐标，dc，dd分别是矩形的长和宽
		for(i=1;i<=A;i++)
		{
			Color d=new Color(0,255-(20*i),(20*i));  //最多12条船的情况下使用渐变颜色绘制船舶矩形
			g.setColor(d);
			try
			{
				da=(int)(50+(x[i+A]-1)*d1);		
				db=(int)(650-(x[i]-1)*d2-TW[i]*d2);		
				dc=(int)(L[i]*d1);
				dd=(int)(TW[i]*d2);
			}catch(ArithmeticException e)
			{			
			}			
			g.fillRect(da,db,dc,dd);
			g.setColor(Color.black);     //船的代码
			g.drawString("船"+i, da+50, db+50);
		}
	}
}
public class B
{
	public static void main(String args[])
	{
		Init f=new Init();
		f.setTitle("分支定界泊位分配");
		f.setSize(1300,700);
		f.setLocation(5,5);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
}
	