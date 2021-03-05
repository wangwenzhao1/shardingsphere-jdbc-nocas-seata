package com.cxytiandi.sharding.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cxytiandi.sharding.po.Order;
import com.cxytiandi.sharding.po.User;
import com.cxytiandi.sharding.po.UserTest;
import com.cxytiandi.sharding.po.UserVO;
import com.cxytiandi.sharding.service.UserService;

import io.seata.core.context.RootContext;

@RestController
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("/users")
	public Object list() {
		return userService.list();
	}

	/**
	 * 分页查询
	 * @author wangwenzhao
	 * @param userVO
	 * @return
	 */
	@PostMapping("/pagenum")
	public Object listpage(@RequestBody UserVO userVO){
        return userService.listPage(userVO);
	}

	/**
	 * 添加user
	 * @author wangwenzhao
	 * @return
	 */
	@GetMapping("/add")
	public Object add() {
		for (int i = 1; i < 30; i++) {
			User user = new User();
			user.setUserid(i);
			user.setCity("深圳" + i);
			user.setName("李四" + i);
			userService.add(user);
		}
		return "success";
	}
	@GetMapping("/addtest")
	public Object addtest() {
	    for (int i = 1; i < 30; i++) {
	        UserTest user = new UserTest();
	        user.setOrderid(i);
	        user.setUserid(i);
	        user.setName("李四" + i);
	        userService.addtest(user);
	    }
	    return "success";
	}

	/**
	 * 添加订单
	 * @author wangwenzhao
	 * @return
	 */
	@GetMapping("/addOrder")
    public Object addOrder() {

        for (int i = 1; i < 30; i++) {
            Order order = new Order();
            order.setUserid(i);
            order.setOrderid(i);
            order.setOrdernum("ss17000" + i);
            order.setName("lisi" + i);
            //order.setAddtime(1615201888);
            //order.setPrice(new BigDecimal(10 + i));
            userService.addOrder(order);
        }
        return "success";
    }

	@GetMapping("/adduser")
	public void getAdduser(){
	    User user = new User();
        user.setUserid(31);
        user.setCity("seata深圳分布式事务");
        user.setName("seata分布式事务");
        userService.add(user);
	}


	@GetMapping("/addOrder/tra")
	//@GlobalTransactional(name = "transaction-text-seata",rollbackFor = Exception.class)
    public void addOrdertra() {
	    System.out.println("tttttt---xid:" + RootContext.getXID());
	    Order order = new Order();
        //order.setAddtime((int) (System.currentTimeMillis() / 1000));
        //order.setPrice(new BigDecimal(10));
        userService.addOrder(order);

    }


	@GetMapping("/adduserandOrder")
	//@Transactional
	//@ShardingTransactionType(TransactionType.XA)
    public Object addOrderTR() {

	    userService.addOrderTR();

	    return "success";
	}

	@GetMapping("/users/{id}")
	public Object get(@PathVariable Integer id) {
		return userService.findById(id);
	}

	@PostMapping("/users/ids")
	public Object get(@RequestBody List<Integer> ids) {
	    return userService.findByIds(ids);
	}

	@PostMapping("/userorder/{id}")
    public Object getUserOrder(@PathVariable Integer id) {
        return userService.getUserOrder(id);
    }
	@PostMapping("/userorderprice/{price}")
	public Object getUserOrderprice(@PathVariable BigDecimal price) {
	    return userService.getUserOrderprice(price);
	}
	@PostMapping("/userorderaddtime/{addTime}")
	public Object getUserOrderaddTime(@PathVariable Integer addTime) {
	    return userService.getUserOrderaddTime(addTime);
	}

	/**
	 *  根据用户名查询
	 * @author wangwenzhao
	 * @param name
	 * @return
	 */
	@PostMapping("/users/query")
	public Object get(@RequestBody String name) {
	    System.out.println("---:name" + name);
		return userService.findByName(name);
	}
	@PostMapping("/users/query1")
	public Object get(@RequestBody UserVO userVO) {
	    System.out.println("---:name111" + userVO.getName());
	    return userService.findByName(userVO.getName());
	}


	@GetMapping("/users/city")
	public Object getcity(String city) {
	    System.out.println("---:city" + city);
	    return userService.findBycity(city);
	}


	/**
	 *  不进行分库分表的数据测试，
	 * @author wangwenzhao
	 * @param name
	 * @return
	 */
	@PostMapping("/zusers/query")
	public Object getzuser(@RequestBody String name) {
	    System.out.println("---:zname" + name);
	    return userService.getzuser(name);
	}
	/**
	 *  不进行分库分表的数据测试，
	 * @author wangwenzhao
	 * @param name
	 * @return
	 */
	@PostMapping("/zusers/query/id")
	public Object getzuserId(@RequestBody Integer id) {
	    System.out.println("---:zid" + id);
	    return userService.getzuserId(id);
	}

	@PostMapping("/zusers/query/age")
	public Object getzuserAge(@RequestBody Integer age) {
	    System.out.println("---:age" + age);
	    return userService.getzuserAge(age);
	}
}
