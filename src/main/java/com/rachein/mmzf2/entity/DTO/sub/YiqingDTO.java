package com.rachein.mmzf2.entity.DTO.sub;

import com.rachein.mmzf2.entity.enums.RiskEnum;
import lombok.Data;

/**
 * @Author 计算机科学系 吴远健
 * @Date 2022/11/30
 * @Description 疫情数据
 */

/**
 *  "area":"广东省茂名市电白区岭门镇除高风险区以外区域",
*   "type":"0"
 */
@Data
public class YiqingDTO {
    private String area;
    private String type;

    @Override
    public String toString() {
        return
                (type.equals("2") ?"高风险":"低风险") + " 地区：" + area + "\n";
    }
}
