package top.api.dto;


import lombok.Data;
import top.api.pojo.Setmeal;
import top.api.pojo.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
