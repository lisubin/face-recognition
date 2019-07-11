package ijinbu.recognition.time;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Shopping {

    public static void main(String[] args) {
        ArrayList<Coupon> data = new ArrayList<>();
        data.add(new Coupon("优惠卷一","满100减10元",100,10,null,null,null,2,true));
        data.add(new Coupon("优惠卷二","20元现金抵扣价",0,20,null,null,null,2,true));
        data.add(new Coupon("优惠卷三","全类品打九折",0,0,"全类品",0.9,null,1,false));
        data.add(new Coupon("优惠卷四","满600减60",600,60,null,null,"2",1,true));
        ArrayList<Coupon> result = new ArrayList<>();
        ArrayList<Coupon> result2 = new ArrayList<>();
        System.out.println("请输入所有金额：");
        try {
            Scanner scanner = new Scanner(System.in);
            Double read = Double.parseDouble(scanner.next());
            double fare1 = 0;
            double fare2 = 0;
            if(read>0){
                //  算出不可叠加的最大金额
                //算出可叠加最大金额
                //找出用户所拥有的所有不可叠加的优惠价，然后找出满足条件的虽大折扣
                /**
                 * 找出用户所用户的所有可叠加的优惠卷，进行优先级排序，
                 * 然后找出满足条件的最优组合(排序的理由：第一可以罗列优惠卷使用的优先级)
                 * 组合最优算法实现  商品总而  减  优惠额    >0  继续优惠  直到 商品总而  减
                 * 优惠额 =0或者小于0时去该次 计算 或者 前一次计算 0.1
                 *  得出结果
                 */

                //
                for(Coupon coupon:data){
                    if(coupon.isAdd()){
                        if(coupon.getMaxFare()<= read ){
                            System.out.println(coupon.getFree());
                            if(coupon.getFree() > 0) {
                                if (coupon.getProType() == null) {
                                    fare1 += coupon.getFree();
                                    result.add(coupon);
                                }
                            }
                        }
                    }else {
                        if(coupon.getFree()==0&&coupon.getDiscount()!=null&&coupon.getProType().equals("全品类")){
                             fare2 = fare2+fare2*coupon.getDiscount();
                            result2.add(coupon);
                        }
                    }
                }

                if(fare1>fare2){
                    for (Coupon c : result) {
                        System.out.println(c.toString());
                    }
                }else {
                    System.out.println(result2.get(0).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
