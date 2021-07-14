package com.select.SelectCourse;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.Administrator;
import com.select.SelectCourse.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SelectCourseApplicationTests {

	@Resource
	private RedisUtil redisUtil;

	@Test
	public void delete(){
		redisUtil.delete("Administrator");
	}

	@Test
	void contextLoads() {
		String str = (String) redisUtil.get("Administrator");
		Administrator administrator =  JSON.parseObject(str,Administrator.class);
		System.out.println(administrator);
	}

}
