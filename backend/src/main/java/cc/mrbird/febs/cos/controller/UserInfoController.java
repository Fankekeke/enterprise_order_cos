package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用户管理 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/user-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoController {

    private final IUserInfoService userInfoService;

    /**
     * 分页获取用户信息
     *
     * @param page     分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<UserInfo> page, UserInfo userInfo) {
        return R.ok(userInfoService.selectUserPage(page, userInfo));
    }

    /**
     * 管理员用户审核
     *
     * @param userId 用户ID
     * @param status 审核状态
     * @return 结果
     */
    @GetMapping("/audit")
    public R audit(Integer userId, String status) {
        return R.ok(userInfoService.update(Wrappers.<UserInfo>lambdaUpdate().set(UserInfo::getStatus, status)
                .set(UserInfo::getAuditDate, DateUtil.formatDateTime(new Date())).eq(UserInfo::getId, userId)));
    }

    /**
     * 根据用户ID获取详情信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/selectDetailByUserId/{userId}")
    public R selectDetailByUserId(@PathVariable Integer userId) {
        return R.ok(userInfoService.selectUserDetail(userId));
    }

    /**
     * 查询用户信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(userInfoService.getById(id));
    }

    /**
     * 查询用户信息详情【用户地址】
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/selectAddressDetail/{id}")
    public R selectAddressDetail (@PathVariable("id") Integer id) {
        return R.ok(userInfoService.selectAddressDetail(id));
    }

    /**
     * 查询用户信息详情【公告信息】
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/selectBulletinDetail/{id}")
    public R selectBulletinDetail(@PathVariable("id") Integer id) {
        return R.ok(userInfoService.selectBulletinDetail(id));
    }

    /**
     * 查询用户信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(userInfoService.list());
    }

    /**
     * 新增用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PostMapping
    public R save(UserInfo userInfo) {
        return R.ok(userInfoService.save(userInfo));
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    @PutMapping
    public R edit(UserInfo userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    /**
     * 删除用户信息
     *
     * @param ids ids
     * @return 用户信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(userInfoService.removeByIds(ids));
    }
}
