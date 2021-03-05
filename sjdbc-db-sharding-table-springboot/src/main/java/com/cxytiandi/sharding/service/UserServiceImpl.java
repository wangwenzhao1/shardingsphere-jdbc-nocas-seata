package com.cxytiandi.sharding.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cxytiandi.sharding.client.TransactionClient;
import com.cxytiandi.sharding.po.Order;
import com.cxytiandi.sharding.po.OrderExtend;
import com.cxytiandi.sharding.po.User;
import com.cxytiandi.sharding.po.UserTest;
import com.cxytiandi.sharding.po.UserVO;
import com.cxytiandi.sharding.po.Zuser;
import com.cxytiandi.sharding.repository.UserRepository;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	TransactionClient transactionClient;

	@Override
    public List<User> list() {
		return userRepository.list();
	}

	@Override
    public Long add(User user) {
	    System.out.println("user 插入：--" + JSONObject.toJSONString(user));
        System.out.println("userRepository----" + userRepository);
		return userRepository.addUser(user);
	}

	@Override
    public Long addtest(UserTest user) {
	    System.out.println("user 插入：--" + JSONObject.toJSONString(user));
        System.out.println("userRepository----" + userRepository);
        return userRepository.addtest(user);
    }

	@Override
	public List<User> findByName(String name) {
	    List<User> user = new ArrayList<>();
	    System.out.println("service:" + name);
	    System.out.println(name);
	    user = userRepository.findByName(name);
	    System.out.println(user.toString());
		return user;
	}

	@Override
	@ShardingTransactionType(TransactionType.BASE)
    public Long addOrder(Order order) {
	    System.out.println("order 插入：--" + JSONObject.toJSONString(order));
	    System.out.println("userRepository----" + userRepository);
	    order.setOrderid(31);
        order.setOrdernum("30seata202008170" + 31);
        order.setUserid(31);
        order.setName("30seata事务");
        return userRepository.addorder(order);
    }

    /* (non-Javadoc)
     * @see com.cxytiandi.sharding.service.UserService#findById(java.lang.Integer)
     */
    @Override
    public User findById(Integer userid) {
        // TODO Auto-generated method stub
        return userRepository.findById(userid);
    }

    /* (non-Javadoc)
     * @see com.cxytiandi.sharding.service.UserService#findById(java.util.List)
     */
    @Override
    public List<User> findByIds(List<Integer> ids) {
        return userRepository.findByIds(ids);
    }

    /* (non-Javadoc)
     * @see com.cxytiandi.sharding.service.UserService#getUserOrder(java.lang.Integer)
     */
    @Override
    public List<OrderExtend> getUserOrder(Integer id) {

        List<OrderExtend> list = new ArrayList<OrderExtend>();
        list = userRepository.getUserOrder(id);
        return list;
    }

    /* (non-Javadoc)
     * @see com.cxytiandi.sharding.service.UserService#addOrderTR()
     */
    @Override
    //@Transactional
    //@ShardingTransactionType(TransactionType.XA)
    @GlobalTransactional
    @ShardingTransactionType(TransactionType.BASE)
    //@GlobalTransactional(name = "my_test_tx_group",rollbackFor = Exception.class)
    public int addOrderTR() {

        System.out.println("------事务tr---xid-:" + RootContext.getXID());

        User user = new User();
        user.setUserid(31);
        user.setCity("seata深圳分布式事务");
        user.setName("seata分布式事务");
        userRepository.addUser(user);

        /*Order order = new Order();
        order.setOrderid(31);
        order.setOrdernum("36seata202008170" + 31);
        order.setUserid(37);
        order.setName("36seata事务");
        order.setAddtime((int) (System.currentTimeMillis() / 1000));
        order.setPrice(new BigDecimal(10));
        userRepository.addOrder(order);*/
        transactionClient.addorder();

        int result = 10/0;
        System.out.println(result);
        return 0;
    }

    @Override
    public List<User> listPage(UserVO userVO) {
        return userRepository.listPage(userVO);
    }

    @Override
    public User findBycity(String city) {
        return userRepository.findBycity(city);
    }

    /* (non-Javadoc)
     * @see com.cxytiandi.sharding.service.UserService#getzuser(java.lang.String)
     */
    @Override
    public Zuser getzuser(String name) {
        return userRepository.getzuser(name);
    }

    /* (non-Javadoc)
     * @see com.cxytiandi.sharding.service.UserService#getzuserId(java.lang.Integer)
     */
    @Override
    public Zuser getzuserId(Integer id) {
        return userRepository.getzuserId(id);
    }

    @Override
    public Zuser getzuserAge(Integer age) {
        return userRepository.getzuserAge(age);
    }

    @Override
    public Order getUserOrderprice(BigDecimal price) {
        return userRepository.getUserOrderprice(price);
    }

    @Override
    public Order getUserOrderaddTime(Integer addTime) {
        return userRepository.getUserOrderaddTime(addTime);
    }


}
