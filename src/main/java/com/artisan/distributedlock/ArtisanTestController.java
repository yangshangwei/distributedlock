package com.artisan.distributedlock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ArtisanTestController {


    @Autowired
    private OrderService orderService;

    @Value("${server.port}")
    private String port;

    @Autowired
    CuratorFramework curatorFramework;

    /**
     * http://127.0.0.1/test 测试ng负载
     * @return
     * @throws Exception
     */
    @GetMapping("/test")
    public String test() {
        return "ok:" + port;
    }

    @PostMapping("/stock/deductNoLock")
    public Object deductNoLock(Integer id) throws Exception {
        orderService.reduceStock(id);
        return "visit:" + port;
    }

    @PostMapping("/stock/deductWithLock")
    public Object deductWithLock(Integer id) throws Exception {

        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/product_" + id);
        try {
            interProcessMutex.acquire();
            orderService.reduceStock(id);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw e;
            }
        }finally {
            interProcessMutex.release();
        }
        return "visit:" + port;
    }


}
