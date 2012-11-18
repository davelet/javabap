package cn.vash.mbca;

//*ʹ�÷�֧���編���м���Ĳ�λ�������*/
//ʹ���б�̬�洢���ݣ�������Խ������
//��֧���編�����Ƚ��ȳ��Ĺ�����Ȳ��Խ��б���
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

/*SIM���а��������μ���ͷ�֧�������ȷ���*/
class SIM
{
	int l,e;
	int i,j,k1,k2,m,n;
	float K;
	
	//PIOVT�������еĻ��任���������ؽ����Ժ��N,B,a,b,c,x��ֵ
	public ArrayList<ArrayList> PIOVT(int N[],int B[],float a[][],float b[],float c[],float v[],float x[],int l,int e,int t)//ʹ���б��ض������ֵ
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
		 b[e]=b[l]/a[l][e];//���㽻���������Xeǰ���ϵ��
		for(k1=0;k1<N.length;k1++)//����Xe�и���Xǰ�潻�������ɵ���ϵ��
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
		 //���ϵõ�������xe���̵�abϵ��
		 //�����if����������������e������0.���x0���̵�a,b��������
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
		//���������xe��abϵ����������Լ��ʽ�У��滻��ʽ�ұߵ�ÿ�γ��֣�����������ʽ��
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
		
		//����������c��ϵ���򽻻������ı�
		for(k1=0;k1<N.length;k1++)
		{
			if(N[k1]!=e)
			{
				j=N[k1];
			    c[j]=c[j]-c[e]*a[e][j];}
			else
				continue;
		}
		c[l]=-c[e]*a[e][l];  //�»�����l��Ŀ�꺯���е�ϵ��ֵ
		b[l]=0;  //����������bϵ����Ϊ0
		c[e]=0;  //���������cϵ����Ϊ0
		for(i=0;i<N.length;i++)//��������l��ԭ���̵ĸ���ϵ����Ϊ0����a
		{
			j=N[i];
			a[l][j]=0;
		
		}
		for(i=0;i<B.length;i++)//�������e��ԭ���̵ĸ���ϵ����Ϊ0����b
		{
			j=B[i];
			a[j][e]=0;
		}
	 for(k1=0;k1<N.length;k1++)//�ѻ�������l����N��
	 {
	 	if(N[k1]==e)
	 	{N[k1]=l;
	 	break;}
	}
	for(k2=0;k2<B.length;k2++)//���������e����B��
	{
		if (B[k2]==l)
		{ B[k2]=e;
		break;}
	}
	return l4;
	}



	//INITIALILE_SIMPLEX�Ƕ��������Թ滮�ж��Ƿ����,Ȼ����г�ʼ������NB����ֵ
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
			
			/*   ���ݹ滮���̵�a��b��c�ó�����B[]
			 *   ֻҪ��a[i][j]������0���ͽ�i���뵽����B��
			 *       ��ͬ��iֻ����һ��                       */
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
			 
			 /*���ݹ滮���̵�a��b��c�ó�����N[]
			  *  ֻҪ��a[i][j]������0���������N���Ƿ���j
			  *  ����оͼ�����һ��a[i][j]������ͽ�j���뵽����N��
			  *      ��ͬ��jֻ����һ��                       */
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
				 
				//��b[i]����СֵE
			 for(i=0;i<b.length;i++)
				{
					if(b[i]<E)
						{
						E=b[i];
					    l=i;
					    }
				}
			 
			 /*************************************
			  * ��b[i]����СֵE<0����и������Թ滮
			  * ����Ŀ�꺯����Ϊx0������x0���뵽�������̵���
			  **************************************/
			 if(E<0) 
			{
				// ��x0���뵽��������ʽ��
				for(i=0;i<B.length;i++)
				{
					if (B[i]!=0)
					{
						j=B[i];
					    a[j][0]=-1;
				    }
				}
				//��0�ӵ�N��
				for(i=N.length-1;i>0;i--)
			          N[i]=N[i-1];	
				N[0]=0;
				//��Ŀ�꺯����Ϊx0������ԭ�е�ϵ��c�ݴ���d��
			    for(i=0;i<c.length;i++)
			      { 
			    	d[i]=c[i];
				    c[i]=0;
				   }
			    c[0]=-1;
			    //0Ϊ���������lΪ�������������л��任
				PIOVT(N,B,a,b,c,v,x,l,0,t);
				j=0;
				k2=0;
				
				//���ж�λ��任����������x[0]��ֵ
		loop:while(k2<N.length)
			{
			//���Nֵ���и�ֵ����x0ֵ��Ϊ0������������
			   if(N[k2]<0) 
			   {
				   x[0]=-1;
				   break;
			   }
			  //������������Ȳ�����ֱ���õ������
			   else  
			   {
				   j=N[k2];
				 //ѡ��Ŀ�꺯����ϵ��Ϊ���ı�����Ϊ�������e�������л��任����
					  if (c[j]>0)
						{
						    e=j;
							l=Integer.MAX_VALUE;
							K=Integer.MAX_VALUE;
							for(i=0;i<B.length;i++)//ȷ����������l
							{
								k1=B[i];
								if (a[k1][e]>0)
								{
									M[k1]=b[k1]/a[k1][e];
									if (M[k1]<K)//ȡ���ϸ�����xe���ӵ�������ȡM[k1]����Сֵ
									{
										K=M[k1];
										l=k1;
									}
								}
								
							}
				          //����޷�ȷ����������l��ֵ�����Թ滮�޽磬����x0��ֵ��Ϊ-1������N��ֵȫ����Ϊ0
							if (K==Integer.MAX_VALUE)
							{
								System.out.println("IN-unbounded");//�޽�
								x[0]=-1;
								for(i=0;i<N.length;i++)
									N[i]=0;
							    break;
							 }
							//����������㣬���л��任�����һ��任�Ժ���Ҫ���½��г�ʼ��
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
					           
			   }//else ������x0��ֵ
				 
			} //loop:while
				//System.out.println("loop stop!!�������Թ滮����");	
				//�ó�x��ֵ
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
			 * ���x0������0������������У�����B��ϵ��ȫ����Ϊ0
			 * ������ȥ������ʽ�е�x0��Ŀ�꺯��תΪԭĿ�꺯��
			 * **********************************************8*/
			if(x[0]!=0)
			{
				System.out.println("infeasible");
				for(i=0;i<B.length;i++)
					B[i]=0;
				
			}
			 else if(x[0]==0)  //(x[0]==0)ԭ�滮����,���������е�x0
			{
				int B1[]=new int[t];
				j=0;
				for(i=0;i<d.length;i++)//B1�д���ԭʼ�ķǻ�������
				{
					if(d[i]!=0)
					{
						B1[j]=i;
						j++;
					}
						
				}
				for(i=0;i<d.length;i++)//��c��ϵ����d��ȡ������Ϊԭʼ����
				{
					for(j=0;j<B.length;j++)
					{
						if(i!=B[j])
						{
							c[i]=d[i];
						}
					}
				}
				//Ŀ�꺯��ϵ��
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
				//x0��abϵ������Ϊ0
				for(i=0;i<a.length;i++)
					a[i][0]=0;
				for(i=0;i<a[0].length;i++)
					a[0][i]=0;
				b[0]=0;
				m=0;
				for( i=0;i<a.length;i++)//�ó�����B[]
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
				 for(i=0;i<a.length;i++)//�ó�����N[]
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
			} //(x[0]==0)ԭ�滮����
		}//�������Թ滮
		
				return l1;

		}

  /************************************************************************8
   * ����SIMPLEX�ǵ����η�������������SIMPLEX�зֱ���û��任�ͳ�ʼ������
   * ������ս����������x��ֵ
   * ***************************************************************************/
	public  float[] SIMPLEX(int N[],int B[],float a[][],float b[],float c[],float v[],float x[],int t)
	{
		float M[]=new float[t];
		//���ȣ����г�ʼ��
		INITIALILE_SIMPLEX(N,B,a,b,c,v,x,t);
		 j=-Integer.MAX_VALUE;
		 k2=-Integer.MAX_VALUE;
		 
		 //�ֱ��������N��B����Сֵ���жϹ滮�Ƿ����
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
		 //���й滮����
		 else if(j!=0&&k2!=0)
		 {
			    j=0;
				k2=0;	 
			loop:while(k2<N.length)
			{
			  j=N[k2];
			  if (c[j]>0&&j!=0)//ѡ��Ŀ�꺯����ϵ��Ϊ���ı�����Ϊ�������e
				{
				     e=j;
					l=Integer.MAX_VALUE;
					K=Integer.MAX_VALUE;
					for(i=0;i<B.length;i++)//ȷ��l
					{
						k1=B[i];
						if (a[k1][e]>0)
						{
							M[k1]=b[k1]/a[k1][e];
							if (M[k1]<K)//ȡ���ϸ�����xe���ӵģ���ȡM[k1]����Сֵ
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
							PIOVT(N,B,a,b,c,v,x,l,e,t);//���û��任����
					
					for(i=0;i<N.length;i++)
					{
						N[i]=0;
					}
					for(i=0;i<B.length;i++)
					{
						B[i]=0;
					}
					//��N��B��Ϊ0�����³�ʼ��
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
		 }//���Թ滮����
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

//����SI��SIM���ɵ�xֵ��飬����С�������λ��������
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
	    	if(p==0||q==0) //���Թ滮�����л����޽�
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
	    	for(i=0;i<x.length;i++)//�����������λС����
			   {
		  		x[i]=(float)((int)(x[i]*100+0.5))/100;
			   }
	    //	v[0]=(float)((int)(v[0]*100+0.5))/100;
	    	v[0]=Math.round(v[0]);
	     return x;
}

//һ�����������ֵ��С����
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
 * ����branch�Ƿ�֧���編����ε��õ����η����м��㣬
 * ֱ���ҵ�һ�������⣬������������ⷵ��
 * x11��v11�ֱ���������x��v�������
 * A1�Ǵ�������t2Ϊ�ǻ��������ĸ�����t1�ǻ���������ǻ�������֮��
 * */
public ArrayList<float[]> Branch(int A1,int T,int M,int TS[],int L[],int TW[],int N[],int B[],float a[][],float b[],float c[],float v[],float x[],int t1,int t2)
{
	int p=0,q,m1,n1;
	int t;
	int ia,ib;
	float k=-1,k1;
	float M1;
	int k2=5000;  //����Ĵ���
	 t=t1+100;   //��ӷ�֧�Ĳ���,�˴�����ӷ�֧�Ĳ���������Ϊ100
	float x1[]=new float[t];//������������ֵ�����ڽ���Ƿ�Ϊ����ֵ���ж�
	float x2[]=new float[t];//�����Ƚ��м����ĳ���֧�Ľ��
	float x11[]=new float[t1];//���Թ滮���ֵʱ����x��ȡֵ,���ս��
	float v11[]=new float[1];//���Թ滮�����ֵv
	float vl=0,vr=0;
	float vn;//ÿ�μ���ʱ���ڱ���v��ֵ
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
	float v22[]=new float[k2];//��¼ÿ��������
	float minz=0,maxz=-Integer.MAX_VALUE;
	float minzfio=0;
	float xfio[]=new float[t1];//�ȵ��ȷ���ʱ����x��ȡֵ
	float vfio[]=new float[1];//�ȵ��ȷ������ֵv
	//float min,max;
		float a1[][]=new float[t][t];
		float a2[][]=new float[100][t2+1];  //�б�����
		float al[][]=new float[t][t];
		float ar[][]=new float[t][t];
	//	float a0[][][]=new float[k2][100][t2+1];//a0[1][2][3]��1Զ����2��3����t1.
		
		float b1[]=new float[t];
		float b2[]=new float[100];//�б�����
		float bl[]=new float[t];
		float br[]=new float[t];
		//float b0[][]=new float[k2][100];
		
		float c1[]=new float[t];
	        SIM s=new SIM();
	     //�Ȳ����ȵ��ȷ����ԭ�����һ��ȫΪ������minzֵ�������Ժ�ļ��������������Ч�����Ч��
	        for(i=0;i<=T;i++)  //�������ռ��ʼ��Ϊ0�����봬�������Ϊ1
	        {
	        	for(j=0;j<=M;j++)
	        	{
	        		aa[i][j]=0;
	        	}
	        }
	        x[1]=TS[1];   //�Ƚ���1�̶���Ȼ�������Ĵ�����������ϰ���
	        x[1+A1]=1;
	        for(i=(int)x[1];i<(x[1]+TW[1]);i++)
	        {
	        	for(j=1;j<1+L[1];j++)
	        	{
	        		aa[i][j]=1;
	        	}
	        }
	        
	   loop1: for(i=2;i<=A1;i++)   //������ֻ�İ���
	       {
	    	   System.out.println("��"+i);  
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
	    				  n1++;    //���������봬���Ƚ�	    				  
	    			  }
	    			//  System.out.println("n="+n);  
	    			 // System.out.println("n1="+n1);  
	    			  if(n1==L[i])   //�ں��������㹻�Ŀռ�
	    			  {
	    				  System.out.println("�ں��������㹻�Ŀռ�");
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
		    				  if(m1==1)  //��ʱ�䷽���ϴ����ص�
	    					  {
		    					System.out.println("��ʱ�䷽���ϴ����ص�"); 
	    						m++;
	    						n=0;
	    						n1=0;
	    						continue loop;
		    				  }
		    				  else       //������Ӧ�Ĵ洢�ռ䣬�򽫴���λ 
		    				  {
		    					  System.out.println("������Ӧ�Ĵ洢�ռ䣬�򽫴���λ ");   
		    					  for(q=m;q<m+TW[i];q++)
			    				  {	    					  
			    				     for(p=n-L[i];p<n;p++)
			    					  {	    						 
			    							 aa[q][p]=1;		    						  
			    					  }	    					 
			    				  }
		    					  x[i]=m;
		    					  System.out.println("ʱ��x["+i+"]="+x[i]);
		    					  x[i+A1]=n-L[i];
		    					  System.out.println("��λx["+i+"]="+x[i+A1]);
		    					  continue loop1;
		    				  }
	    				  } 
	    			  }
	    		  }	 
	    	  }
	       }
	       
	       for(i=1;i<=A1;i++)    //���ʱ����Сֵ��minzfio��ʼΪ0��Ŀ��ֵΪ�����������ü�
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
	       System.out.println("��ʼʱminz="+minz);
	       if(minz==-A1)
	       {
	    	   minz=-Integer.MAX_VALUE;   
	       }
	        //v22��ʼ��Ϊ������
	      /*  for(i=0;i<v22.length;i++)
	        {
	        	v22[i]=-Integer.MAX_VALUE;
	        }*/
	      //ԭ��abc���ֲ��䣬ʹ��a1b1c1���м���
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
	       for(i=0;i<x.length;i++)//������Թ滮���xֵ
	       {
	    	   if(x[i]>k)
	    	   {
	    		  k=x[i];
	    	   }
	       }
	       p=0;
	       if(k==0)//���xֵ��Ϊ0�������Թ滮�޽�򲻿���
	    	   System.out.print("���Թ滮�޽�򲻿���");
	       else if(k!=0&&v[0]<=minz)
	       {
	    	   return l6;
	       }
	       else  if(k!=0&&v[0]>minz)//���Թ滮����
	       {
	    	  // v22[0]=v[0];
	    	   maxz=v[0];
	    	   i=1;	    	
	    	    while(i<=t2)//ֻ���t2��xֵ�Ƿ�Ϊ����
	    	      	{  
//**************************�ʼʱ���еķ�֧����**********************************8
	    	    	   x1[i]=Math.round(x[i]);//����ֵ
	    	    	   if(Math.abs(x1[i]-x[i])>0.001)//���x[i]�������������з�֧�޽�
	    	    	   {
	    	    		   M1=x[i];	    	    		   
	    	    		   System.out.println("��x["+i+"]="+(Math.ceil(M1)-1));
		  	          		for(m=0;m<a.length;m++)
		        				for(n=0;n<a[m].length;n++)
		        					a1[m][n]=a[m][n];
		        			for(m=0;m<b.length;m++)
		        				b1[m]=b[m];
		        			for(m=0;m<c.length;m++)
		        			   c1[m]=c[m];
		        			
		        			 a1[t1+1][i]=1;//x[i]�������A1
		         		     b1[t1+1]=(float)Math.ceil(M1)-1; 
		         		     al[t1+1][i]=1;//x[i]�������Al
		         		     bl[t1+1]=(float)Math.ceil(M1)-1; 
		  	        			for(m=0;m<N.length;m++)
		  	        				N[m]=0;
		  	        			for(m=0;m<B.length;m++)
		  	        			   B[m]=0;
		  	          		    for(m=0;m<x.length;m++)
		  	         			   x[m]=0;
		  	          		       v[0]=0;	         
		  	          				  	          		    
		  	       			    x=s.SI(N, B, a1, b1, c1,v, x, t); 
		  	       			    //���������a1��b1��ֵ��Ϊ0
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
		  	      				  x2[m]=x[m];  //��x������浽x2��	  	      				    	      				 
		  	      			  }
		  	       			 vl=v[0];     //v�Ľ����vl�ݴ�		  	       			
		  	       			 System.out.println("vl="+vl);  
		  	       		 //*********�ж����Թ滮�Ƿ����*******
		  	       		    k=-1;
		  	       		    for(m=0;m<x2.length;m++)//k���������ֵ
	        		         {
	        		    	   if(x2[m]>k)
	        		    	   {
	        		    		  k=x2[m];
	        		    	   }
	        		         }
		  	       		 System.out.println("������ֵ�Ƿ��з�����ֵ");	 
		  	       		 if(k!=0&&vl>minz&&vl<=maxz)//���ֵ��Ч�������
	     				  { 
		  	       		      m=1;	  	        					   
	               			  while(m<=t2)
	               			  {
	               					  x1[m]=Math.round(x2[m]);//����ֵ
	                 		    	   if(x1[m]-x2[m]!=0)//x[i]��������
	                 		    	   {	                 		    		 
	                 		    		   System.out.println("�����룺x["+m+"]="+x2[m]);
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
	               			if(m==t2+1)//���Թ滮�ṹȫΪ��������>minz
	               			  {	
	               			      System.out.println("�����ȫΪ����");
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
	               				  // j++; //���ȫΪ�����Ҵ����Ҳ࣬ɾ���Ҳ��֧
	               				 //  continue loop;
	               			} //���Թ滮�ṹȫΪ��������>minz
	           			  } //�µ����Թ滮����  
		  	       		 else
		  	       		 {
		  	       			 System.out.println("���ֵ��Ч");
		  	       		 }	     				 
		  	       		 		  	       		
		  	       		//***********�Ҳ�*******************
		  	       			System.out.println("��x[i]="+(Math.ceil(M1)));
		  	       		for(m=0;m<a.length;m++)
	        				for(n=0;n<a[m].length;n++)
	        					a1[m][n]=a[m][n];
	        			for(m=0;m<b.length;m++)
	        				b1[m]=b[m];
	        			for(m=0;m<c.length;m++)
	        			   c1[m]=c[m];
	        			            	
		             		      a1[t1+1][i]=-1;//x[i]�������A1
		            		      b1[t1+1]=(float)-Math.ceil(M1); 
		            		      ar[t1+1][i]=-1;//x[i]�������Ar
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
		  	            		
		  	                //*********�ж����Թ滮�Ƿ����*******
		  	        			 k1=-1; 	        			 
		  	        	
		  	        			  for(m=0;m<x.length;m++)//k1���Ҳ�����ֵ
		  	       		         {
		  	       		    	   if(x[m]>k1)
		  	       		    	   {
		  	       		    		  k1=x[m]; 
		  	       		    	   }
		  	       		         }
		  	        			  	        		   	  	        				  
		  	        			 //�鿴x[i]ֵ�Ƿ�Ϊ����
		  	        			 System.out.println("����Ҳ��Ƿ��з�����ֵ");
		  	        				  	        	
		  	        				 if(k1!=0&&vr>minz&&vr<=maxz)//�Ҳ�ȡֵ
		  	        				  { 	  	        					
		  	        					  m=1;
		  	                			  while(m<=t2)
		  	                			  {     				  
		  	                                 x1[m]=Math.round(x[m]);//����ֵ
		  	                   		    	   if(x1[m]-x[m]!=0)//x[i]��������
		  	                   		    	   {
		  	                   		    		   if(l1.size()==0)
		  	                   		    		   {
		  	                   		    			   p=0;
		  	                   		    		   }
		  	                   		    		   else
		  	                   		    		   {
		  	                   		    			 p++; 
		  	                   		    		   }		  	                   		    		  
		  	                   		    		 System.out.println("�Ҳ���룺x["+m+"]="+x[m]);
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
		  	                			  {	   System.out.println("�Ҳ�ȫΪ����");	                				 
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
		  	 	  	       			 System.out.println("�Ҳ�ֵ��Ч");
		  	 	  	       		 }	  	        			
		  	        		 
	    	    		    break;
	    	    		}
	    	    	   else//���x[i]�������鿴x[i+1]
	    	    		   i++;
	    	    	 }
	    	  if(i==t2+1)//���x[]ȫ����������x����x11
	    	 	{  	    		 
	    			  minz=v[0];
	        		  for(j=0;j<x.length;j++)//������ʱ��ȡֵ�洢
	        			  {
	        				  x11[j]=x[j];
	        			  } 
	        		  v11[0]=v[0];	    		  	    		     
	    	 	}
 //***************************��ʽ�ķ�֧�������***********************
	    	  else//���x[]��ȫ������������з�֧�޽�
	    	  { 
	    		  if(l1.size()==0)
	    		  {
	    			  return l6;
	    		  }
	    		  else
	    		  {
	    			  j=0;  //ÿ�μ������	    		 
	  	    	    while(j<=p)//���б��е�ֵʹ�÷�֧�޽編
	  	        	  {  
	  	    		  
	  	    			  //ȡ��ԭ�е�abcϵ��
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
	  	        		  
	  	        		 /* System.out.println("ȡ��");
	  	        		   for(m1=1;m1<=ib;m1++)
	  	          		       {
	  	          		    	   for(n1=1;n1<=t2;n1++)
	  	          		    	   {
	  	          		    		System.out.print(a2[m1][n1]+" ");
	  	          		    	   }
	  	          		    	   System.out.print("="+b2[m1]);
	  	          		    	  System.out.println();
	  	          		       }*/
	  	        		  //���q��ֵ��q�Ƿ�֧�Ĳ��������¼���Ļ��������ĸ���
	  	        		 // System.out.println("��ʱp="+p);
	  	        		  //System.out.println("��ʱib="+ib);
	  	        		  //System.out.println("��ʱia="+ia);
	  	        		//�����������������ֱ�������������м���
	  	        		  if(vn>maxz||vn<minz) //?????????������ȺŻ���Σ�������������
	  	        		  {
	  	        			  j++;
	  	        		  }
	  	        		   
	  	        		  else   //���򣬽��з�֧����ļ���
	  	        		  {       			    
	  	  	        	  //***********���*******************	  	      			
	  	  	          		   System.out.println("��x["+i+"]="+(Math.ceil(M1)-1));
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
	  	        			 a1[t1+1+ib][i]=1;//x[i]�������A1
	  	         		     b1[t1+1+ib]=(float)Math.ceil(M1)-1; 
	  	         		     al[t1+1+ib][i]=1;//x[i]�������Al
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
	  	  	       			    //���������a1��b1��ֵ��Ϊ0
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
	  	  	      				  x2[m]=x[m];  //��x������浽x2��	  	      				    	      				 
	  	  	      			  }
	  	  	       			 vl=v[0];     //v�Ľ����vl�ݴ�		  	       			
	  	  	       			 System.out.println("vl="+vl);  
	  	  	       			 
	  	  	       		System.out.println("��x[i]="+(Math.ceil(M1)));
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
	  	             		      a1[t1+1+ib][i]=-1;//x[i]�������A1
	  	            		      b1[t1+1+ib]=(float)-Math.ceil(M1); 
	  	            		      ar[t1+1+ib][i]=-1;//x[i]�������Ar
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
	  	  	       			 
	  	  	       		 //*********�ж�������Թ滮�Ƿ����*******
	  	  	       		    k=-1;
	  	  	       		    for(m=0;m<x2.length;m++)//k���������ֵ
	          		         {
	          		    	   if(x2[m]>k)
	          		    	   {
	          		    		  k=x2[m];
	          		    	   }
	          		         }
	  	  	       		  k1=-1; 	        			 
	  		  	        	
	  	        			  for(m=0;m<x.length;m++)//k1���Ҳ�����ֵ
	  	       		         {
	  	       		    	   if(x[m]>k1)
	  	       		    	   {
	  	       		    		  k1=x[m]; 
	  	       		    	   }
	  	       		         }
	  	        			  
	  	        			  if(vl>=vr)
	  	        			  {
	  	        				  System.out.println("������ֵ�Ƿ��з�����ֵ");	 
	  	    	  	       		  if(k!=0&&vl>minz&&vl<=maxz)//���ֵ��Ч�������
	  	         				  { 	    	  	       			  
	  	         					   m=1;	  	        					   
	  	                   			  while(m<=t2)
	  	                   			  {               				  
	  	                   					  x1[m]=Math.round(x2[m]);//����ֵ
	  	                     		    	   if(x1[m]-x2[m]!=0)//x[i]��������
	  	                     		    	   {
	  	                     		    		   if(m==i)
	  	                     		    		   {
	  	                     		    			   break;
	  	                     		    		   }
	  	                     		    		   else
	  	                     		    		   {
	  	                     		    			  p++;
	  		                     		    		   System.out.println("�����룺x["+m+"]="+x2[m]);
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
	  	                   			  
	  	                   			if(m==t2+1)//���Թ滮�ṹȫΪ��������>minz
	  	                   			  {	
	  	                   			      System.out.println("�����ȫΪ����");
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
	  	                   				  // j++; //���ȫΪ�����Ҵ����Ҳ࣬ɾ���Ҳ��֧
	  	                   				 //  continue loop;
	  	                   			} //���Թ滮�ṹȫΪ��������>minz              				       	    
	  	               			  } //�µ����Թ滮����  
	  	    	  	       		 else
	  	    	  	       		 {
	  	    	  	       			 System.out.println("���ֵ��Ч");
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
	  	    	  	            		
	  	    	  	                //*********�ж��Ҳ����Թ滮�Ƿ����*******
	  	    	  	        			
	  	    	  	        			  	        		   	  	        				  
	  	    	  	        			 //�鿴x[i]ֵ�Ƿ�Ϊ����
	  	    	  	        			 System.out.println("����Ҳ��Ƿ��з�����ֵ");
	  	    	  	        				  	        	
	  	    	  	        				 if(k1!=0&&vr>minz&&vr<=maxz)//�Ҳ�ȡֵ
	  	    	  	        				  { 	  	        					
	  	    	  	        					  m=1;
	  	    	  	                			  while(m<=t2)
	  	    	  	                			  {  
	  	    	  	                					x1[m]=Math.round(x[m]);//����ֵ
	  	    	  	                   		    	   if(x1[m]-x[m]!=0)//x[i]��������
	  	    	  	                   		    	   {
	  	    	  	                   		    		 if(m==i)
	  	  	                     		    		     {
	  	  	                     		    			   break;
	  	  	                     		    		      }
	  	    	  	                   		    		 else
	  	    	  	                   		    		 {
	  	    	  	                   		    		 p++;
	  	    	  	                   		    		 System.out.println("�Ҳ���룺x["+m+"]="+x[m]);
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
	  	    	  	                			  {	   System.out.println("�Ҳ�ȫΪ����");	                				 
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
	  	    	  	 	  	       			 System.out.println("�Ҳ�ֵ��Ч");
	  	    	  	 	  	       		 }
	  	    	  	        			  	        				
	  	    	  	        			 //���������ֵ��Ϊ0  	        			
	  	                        			// v22[j]=-Integer.MAX_VALUE;
	  	                        		 //��maxz��ȡֵ
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
	  	        				  //*********�ж��Ҳ����Թ滮�Ƿ����*******  	        			
	  	   	  	        				 
	   	  	        			 System.out.println("����Ҳ��Ƿ��з�����ֵ");
	   	  	        				  	        	
	   	  	        				 if(k1!=0&&vr>minz&&vr<=maxz)//�Ҳ�ȡֵ
	   	  	        				  { 	  	        					
	   	  	        					  m=1;
	   	  	                			  while(m<=t2)
	   	  	                			  {  
	   	  	                					x1[m]=Math.round(x[m]);//����ֵ
	   	  	                   		    	   if(x1[m]-x[m]!=0)//x[i]��������
	   	  	                   		    	   {
	   	  	                   		    	     if(m==i)
	                     		    		         {
	                     		    			        break;
	                     		    		         }
	   	  	                   		    	     else
	   	  	                   		    	     {
	   	  	                   		    	   p++;
	  	  	                   		    		 System.out.println("�Ҳ���룺x["+m+"]="+x[m]);
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
	   	  	                			  {	   System.out.println("�Ҳ�ȫΪ����");	                				 
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
	   	  	 	  	       			 System.out.println("�Ҳ�ֵ��Ч");
	   	  	 	  	       		 }
	   	  	        			  	        				
	   	  	        			 //���������ֵ��Ϊ0  	        			
	                       			// v22[j]=-Integer.MAX_VALUE;
	                       		 //��maxz��ȡֵ
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
	  	        				  System.out.println("������ֵ�Ƿ��з�����ֵ");	 
	  	    	  	       		  if(k!=0&&vl>minz&&vl<=maxz)//���ֵ��Ч�������
	  	         				  {     					
	  	         					   m=1;	  	        					   
	  	                   			  while(m<=t2)
	  	                   			  {               				  
	  	                   					  x1[m]=Math.round(x2[m]);//����ֵ
	  	                     		    	   if(x1[m]-x2[m]!=0)//x[i]��������
	  	                     		    	   {	
	  	                     		    		  if(m==i)
	  	                     		    		   {
	  	                     		    			   break;
	  	                     		    		   }
	  	                     		    		  else
	  	                     		    		  {
	  	                     		    			 p++;
	  		                     		    		   System.out.println("�����룺x["+m+"]="+x2[m]);
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
	  	                   			  
	  	                   			if(m==t2+1)//���Թ滮�ṹȫΪ��������>minz
	  	                   			  {	
	  	                   			      System.out.println("�����ȫΪ����");
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
	  	                   				  // j++; //���ȫΪ�����Ҵ����Ҳ࣬ɾ���Ҳ��֧
	  	                   				 //  continue loop;
	  	                   			} //���Թ滮�ṹȫΪ��������>minz              				       	    
	  	               			  } //�µ����Թ滮����  
	  	    	  	       		 else
	  	    	  	       		 {
	  	    	  	       			 System.out.println("���ֵ��Ч");
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
	  	  	        				System.gc();//��������	

	  	        		  }
	  	        		 
	  	        	  }//���б��е�ֵʹ�÷�֧�޽編  
	    		  }
	    		 
	    	  
	    	  }//���x[]��ȫ������������з�֧�޽�
	       
	       }//���Թ滮����
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
 *  plan���ݴ�����Ϣ�������Թ滮���̣����ط��̵�ϵ��
 */
	public ArrayList<float[]> plan(int A,int T,int M,int CRS,int TS[],int W[],int L[],int C[],int TW[],int PR[])
	{
		
		ArrayList<int[]> l1;
		ArrayList<float[]> l2;
		ArrayList<ArrayList> l3;

		 L2=2*A+A*A*2; //�ǻ��������ĸ���
		 //L1=2*A*(A-1)+6*A+3*A*(A-1)/2+A*A*2;
		 L1=4*A+A*A*2+4*A*A+3*A*(A-1)/2;  //�ǻ��������ӻ��������ĸ���
		 L0=L1+100;
	     N=new int[L0]; 
		 B=new int[L0]; 
		 x=new float[L0];  
		 xl=new float[L0];  
		 b=new float[L0]; 
		 a=new float[L0][L0]; 
		 c=new float[L0]; 
		 vl=Integer.MIN_VALUE;
		 System.out.println("��������������"+(L1-L2));
		 System.out.println("�ǻ�������������"+L2);
		 ArrayList<float[]> l4=new ArrayList<float[]>();
		 l4.add(x);
		 l4.add(v);

			//*******����a.b.c��ֵ*��ʼ����Ϊ0***********
		     for(i=0;i<a.length;i++)
				 for(j=0;j<a[i].length;j++)
					 a[i][j]=0;
			 for(i=0;i<b.length;i++)
				 b[i]=0;
			 for(i=0;i<c.length;i++)
				 c[i]=0;
			 
			 //Ŀ�꺯��  �ڸ�ʱ����̣�����ʼʱ��֮����С
			 for(t=1;t<=A;t++)
				{
					c[t]=-1;
				}
			 
			//�������Ҵ�������
			//System.out.println("A="+A);
	     	 //System.out.println("�������Ҵ�������");	
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
				}//�������Ҵ�������
				
				
				
				//��λʱ���λ��Լ��
				//System.out.println("��λʱ���λ��Լ��");		
			    for(i=1;i<=A;i++)
			    {
			    	a[k1][i]=-1; //��λʱ����ڵ���ʱ��
			    	b[k1]=-TS[i];
			    	k1++;
			    	a[k1][A+i]=-1;//�ռ����1
			    	b[k1]=-1;
			    	k1++;
			    	a[k1][i]=1;//����ʱ��С�ڵ���T
			    	b[k1]=T-TW[i]+1;
			    	k1++;
			    	a[k1][A+i]=1;//����λ��С�ڵ���M
			    	b[k1]=M-L[i]+1;
			    	k1++;
			    }
			    
			    
			    //�ŵ�Լ��
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
				System.out.println("��ʼ���в�λ���䣺");	
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

					
				    	//����ʱ�̷����ŵ���������

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
	    jb11=new JButton("��");
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
				
			fd=new FileDialog(this,"���ļ�",FileDialog.LOAD);	
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
				int t0[]=new int [1000];//��������
				int t10[]=new int [1000];//С������
				int b0;
				int h0[]=new int[1000];
				/*�Ƚ��ļ��е�������ȫ����h0�У��ٽ����еĿո�س�ɾ�����ر�
				 * �ǰ�һ���������ָ���ʵ���������ʮλ�����λ������a0��*/
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
			 jb22=new JButton("��һ��");
			 jb33=new JButton("��ʾ���");
			 jp6.setVisible(true);
			 jp6.add(jt0,BorderLayout.CENTER);
			 jp7.add(jb22,FlowLayout.LEFT);
			 jp7.add(jb33,FlowLayout.CENTER);
			 jp6.add(jp7,BorderLayout.SOUTH);
			 jt0.append("���ĸ�����"+A+"\n");
			 jt0.append("��λ�ĸ�����"+M+"\n");
			 jt0.append("ʱ������"+T+"\n");
			 jt0.append("�ŵ��ĸ�����"+CRS+"\n");
			 jt0.append("\n"+"����ʱ�̣�"+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(TS[i]+"  ");
				}
			 jt0.append("\n"+"������ҵ����"+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(W[i]+"  ");
				}
			 jt0.append("\n"+"������"+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(L[i]+"  ");
				}
			 jt0.append("\n"+"�ƻ�����·����"+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(C[i]+"  ");
				}
			 jt0.append("\n"+"ƫ�ã�"+"\n");
			 for(i=1;i<=A;i++)
				{   
					jt0.append(PR[i]+"  ");
				}
			 jt0.append("\n"+"����ʱ�䣺"+"\n");
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
		     jb44=new JButton("����");
		     jp8.add(jb44,FlowLayout.LEFT);
		     jt11.append("\n"+A+"�����Ĳ�λ������£�"+"\n");
		      
			   if(v[0]==-Integer.MAX_VALUE)
				   jt11.append("�޿���������");
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
			 jb55=new JButton("ֱ��ͼ");
			 jb66=new JButton("�˳�");
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
		else if(e.getSource()==jb66) //�رմ���
		{
			System.exit(0);
		}
		}
	public void paint(Graphics g)
	{
		g.drawLine(50,50,50,650);
		g.drawLine(50,50,40,60);//��ͷ
		g.drawLine(50,50,60,60);
		g.drawLine(50,650,1260,650);
		g.drawLine(1260,650,1250,640);//��ͷ
		g.drawLine(1260,650,1250,660);
		try
		{
			d1=1200/M;		
			d2=480/T;
		}catch(ArithmeticException e)
		{			
		}
		Color c=new Color(255,255,0);//��ɫ-���갵��
		int da=0,db=0,dc=0,dd=0;
		for(i=1;i<=M;i++)   //����
		{
			g.setColor(c);
			g.drawLine(50+i*d1, 650,50+i*d1,50);
			g.setColor(Color.black);    //��ɫ����
			g.drawString(""+i, 30+i*d1, 670);
		}
		for(i=1;i<=T;i++)    //����
		{
			g.setColor(c);
			g.drawLine(50,650-i*d2,1250,650-i*d2);
			g.setColor(Color.black);   //��ɫ����
			g.drawString(""+i, 30, 665-i*d2);
		}
	    	g.setColor(Color.black);			
			g.drawString("��λ",1250,680);
			g.drawString("ʱ��",20,100);
		
		//da��db���ƾ��ε����Ͻ����꣬dc��dd�ֱ��Ǿ��εĳ��Ϳ�
		for(i=1;i<=A;i++)
		{
			Color d=new Color(0,255-(20*i),(20*i));  //���12�����������ʹ�ý�����ɫ���ƴ�������
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
			g.setColor(Color.black);     //���Ĵ���
			g.drawString("��"+i, da+50, db+50);
		}
	}
}
public class B
{
	public static void main(String args[])
	{
		Init f=new Init();
		f.setTitle("��֧���粴λ����");
		f.setSize(1300,700);
		f.setLocation(5,5);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
}
	