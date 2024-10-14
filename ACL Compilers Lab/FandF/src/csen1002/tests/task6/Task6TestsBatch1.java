package csen1002.tests.task6;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Timeout.ThreadMode;

import csen1002.main.task6.CfgFirstFollow;

@Timeout(value = 5, unit = TimeUnit.SECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
public class Task6TestsBatch1 {

	@Test
	public void testCfg1First() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;V;Q;E;J;T;A#a;c;l;n;q;s;x#S/lSnV,QQSSE,Vn,SSs,Q,AAl;V/Jl,QAcE,SqSsS,e,V;Q/c,VSnE;E/x,VQ,V,aEcJs,E,S,Q;J/TJ,qTlQ,e;T/lVQ,q,lVEQV,x,e;A/a,T");
		assertEquals("S/aclnqx;V/acelnqx;Q/aclnqx;E/acelnqx;J/elqx;T/elqx;A/aelqx", cfgFirstFollow.first());
	}
	
	@Test
	public void testCfg1Follow() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;V;Q;E;J;T;A#a;c;l;n;q;s;x#S/lSnV,QQSSE,Vn,SSs,Q,AAl;V/Jl,QAcE,SqSsS,e,V;Q/c,VSnE;E/x,VQ,V,aEcJs,E,S,Q;J/TJ,qTlQ,e;T/lVQ,q,lVEQV,x,e;A/a,T");
		assertEquals("S/$aclnqsx;V/$aclnqsx;Q/$aclnqsx;E/$aclnqsx;J/ls;T/aclqsx;A/aclqx", cfgFirstFollow.follow());
	}

	@Test
	public void testCfg2First() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;D;N;X;T;F;L#a;b;c;o;v;w#S/SFL,ScL,N;D/TbLvN,aX,S,T;N/Xw,NoS,wXDw,aNNSL,L;X/w,SwLX,v,XaS,NoS,e,T;T/XLTDw,cDTvT,XcX,X;F/aTLTD,Xc,c,TNo,D,LDvF,e,S;L/aLwLc,LNXw");
		assertEquals("S/acvw;D/abcevw;N/acvw;X/acevw;T/acevw;F/abcevw;L/a", cfgFirstFollow.first());
	}
	
	@Test
	public void testCfg2Follow() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;D;N;X;T;F;L#a;b;c;o;v;w#S/SFL,ScL,N;D/TbLvN,aX,S,T;N/Xw,NoS,wXDw,aNNSL,L;X/w,SwLX,v,XaS,NoS,e,T;T/XLTDw,cDTvT,XcX,X;F/aTLTD,Xc,c,TNo,D,LDvF,e,S;L/aLwLc,LNXw");
		assertEquals("S/$abcovw;D/acvw;N/$abcovw;X/abcvw;T/abcvw;F/a;L/$abcovw", cfgFirstFollow.follow());
	}

	@Test
	public void testCfg3First() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;R;L;W;V;B;K#a;b;d;l;q;z#S/dWq,V;R/lLa,lBL,bSKSR,VRWR;L/zBKK,zWqKW,l,zLS,lWl,dVdSq,e;W/zLaS,VV,V,S;V/dWRz,aWd,e,S,V;B/VKd,KVdL;K/LBa,VV,BbL");
		assertEquals("S/ade;R/abdl;L/delz;W/adez;V/ade;B/adlz;K/adelz", cfgFirstFollow.first());
	}
	
	@Test
	public void testCfg3Follow() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;R;L;W;V;B;K#a;b;d;l;q;z#S/dWq,V;R/lLa,lBL,bSKSR,VRWR;L/zBKK,zWqKW,l,zLS,lWl,dVdSq,e;W/zLaS,VV,V,S;V/dWRz,aWd,e,S,V;B/VKd,KVdL;K/LBa,VV,BbL");
		assertEquals("S/$abdlqz;R/abdlz;L/abdlz;W/abdlqz;V/$abdlqz;B/abdlz;K/abdlz", cfgFirstFollow.follow());
	}

	@Test
	public void testCfg4First() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;V;Z;Q;O;M#g;j;l;r;s;w#S/wV,V,VVSQs,sV,O;V/l,VMgVO,V;Z/SrQl,SsVOj,e,Q,O,V;Q/sZgZg,Z,MM,gOVVj,MVw,O;O/lSVV,S,e;M/QQO,rZSOV,SsSw");
		assertEquals("S/elsw;V/l;Z/eglrsw;Q/eglrsw;O/elsw;M/eglrsw", cfgFirstFollow.first());
	}
	
	@Test
	public void testCfg4Follow() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;V;Z;Q;O;M#g;j;l;r;s;w#S/wV,V,VVSQs,sV,O;V/l,VMgVO,V;Z/SrQl,SsVOj,e,Q,O,V;Q/sZgZg,Z,MM,gOVVj,MVw,O;O/lSVV,S,e;M/QQO,rZSOV,SsSw");
		assertEquals("S/$gjlrsw;V/$gjlrsw;Z/glrsw;Q/glrsw;O/$gjlrsw;M/glrsw", cfgFirstFollow.follow());
	}

	@Test
	public void testCfg5First() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;Y;G;N;Z;D;M#b;c;d;k;n;s;x#S/k,n,kMDx,bYxGM;Y/cMD,Ys,MD,kNN,NDnDM,G,Z;G/MY,ZkNN,N;N/DZZGk,MGYS,GbSN,Gb,ZY,e,D;Z/GnM,SZYM,x,M,e,D,S;D/DSn,MbZ;M/DSdD,D");
		assertEquals("S/bkn;Y/bceknsx;G/bceknsx;N/bceknsx;Z/bceknsx;D/;M/", cfgFirstFollow.first());
	}
	
	@Test
	public void testCfg5Follow() {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;Y;G;N;Z;D;M#b;c;d;k;n;s;x#S/k,n,kMDx,bYxGM;Y/cMD,Ys,MD,kNN,NDnDM,G,Z;G/MY,ZkNN,N;N/DZZGk,MGYS,GbSN,Gb,ZY,e,D;Z/GnM,SZYM,x,M,e,D,S;D/DSn,MbZ;M/DSdD,D");
		assertEquals("S/$bcdknsx;Y/bcknsx;G/bcknsx;N/bcknsx;Z/$bcdknsx;D/$bcdknsx;M/$bcdknsx", cfgFirstFollow.follow());
	}
	
}