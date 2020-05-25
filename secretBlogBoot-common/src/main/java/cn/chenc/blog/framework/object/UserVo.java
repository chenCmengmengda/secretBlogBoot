package cn.chenc.blog.framework.object;

import cn.chenc.blog.business.entity.SysUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <p>
 *
 * </p>
 *
 * @author chenc
 * @since 2020-03-21
 */
@ToString(callSuper = true)
public class UserVo extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Getter
    private Integer id;

    @Getter
    private String username;

    @Getter
    private String password;


    /**
     * 外键:角色id
     */
    @Getter
    private Integer roleId;


    public UserVo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Integer id, Integer roleId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }
}
