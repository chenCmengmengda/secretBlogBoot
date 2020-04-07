package cn.chenc.blog.business.service.impl;

import cn.chenc.blog.business.entity.Photo;
import cn.chenc.blog.business.mapper.PhotoMapper;
import cn.chenc.blog.business.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenc
 * @since 2020-04-07
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

}
