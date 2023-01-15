package top.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.api.common.BaseContext;
import top.api.common.R;
import top.api.pojo.AddressBook;
import top.api.service.AddressBookService;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        // 从线程中读取 userId
        addressBook.setUserId(BaseContext.get());
        addressBookService.save(addressBook);
        return R.success("ok",addressBook);
    }

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        // 将所有地址全部设置成 0不默认
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId,BaseContext.get());
        updateWrapper.set(AddressBook::getIsDefault,0);
        addressBookService.update(updateWrapper);

        // 设置本次默认
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);

        return R.success("ok");
    }

    /**
     * 获取默认地址
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,BaseContext.get());
        queryWrapper.eq(AddressBook::getIsDefault,1);

        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (addressBook == null){
            return R.error("没有找到!");
        }
        return R.success("ok",addressBook);
    }

    /**
     * 查询用户全部地址
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> getList(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,BaseContext.get());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        List<AddressBook> list = addressBookService.list(queryWrapper);

        return R.success("ok",list);
    }

    /**
     * 删除地址
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        addressBookService.removeById(ids);
        return R.success("success");
    }

    /**
     * 获取指定地址信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> getOneAddress(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return R.success("ok",addressBook);
    }

    /**
     * 修改指定地址信息
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> updateAddress(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("ok");
    }

}
