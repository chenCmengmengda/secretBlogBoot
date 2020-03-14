package cn.chenc.blog.business.mapper;

import cn.chenc.blog.business.entity.TbSysParameter;
import cn.chenc.blog.business.entity.TbSysParameterExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TbSysParameterMapper {
    long countByExample(TbSysParameterExample example);

    int deleteByExample(TbSysParameterExample example);

    int deleteByPrimaryKey(String cmsname);

    int insert(TbSysParameter record);

    int insertSelective(TbSysParameter record);

    List<TbSysParameter> selectByExample(TbSysParameterExample example);

    TbSysParameter selectByPrimaryKey(String cmsname);

    int updateByExampleSelective(@Param("record") TbSysParameter record, @Param("example") TbSysParameterExample example);

    int updateByExample(@Param("record") TbSysParameter record, @Param("example") TbSysParameterExample example);

    int updateByPrimaryKeySelective(TbSysParameter record);

    int updateByPrimaryKey(TbSysParameter record);
}
