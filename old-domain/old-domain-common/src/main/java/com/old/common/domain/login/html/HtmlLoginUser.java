package com.old.common.domain.login.html;

import com.old.common.domain.login.LoginUser;
import com.old.common.domain.login.MenuBo;
import com.old.common.domain.login.RoleBo;
import com.old.common.domain.login.RouterBo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class HtmlLoginUser implements LoginUser {

    private HtmlUserBo userBo;

    private List<RoleBo> roleBos;

    private List<MenuBo> menuBos;

    private Set<String> menuPaths;

    private List<RouterBo> routers;

    private String ipAddress;
    private String token;
    private LocalDateTime loginTime;


    public HtmlLoginUser(Builder builder) {
        this.userBo = builder.userBo;
        this.roleBos = builder.roleBos;
        this.menuBos = builder.menuBos;
        this.menuPaths = builder.menuPaths;
        this.routers = builder.routers;
        this.ipAddress = builder.ipAddress;
        this.token = builder.token;
        this.loginTime = builder.loginTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Integer loginUserId() {
        return userBo.getId();
    }

    @Override
    public String loginUserName() {
        return userBo.getUserName();
    }

    public static class Builder {
        private HtmlUserBo userBo;

        private List<RoleBo> roleBos;

        private List<MenuBo> menuBos;

        private Set<String> menuPaths;

        private List<RouterBo> routers;

        private String ipAddress;

        private String token;

        private LocalDateTime loginTime;

        private Builder self = this;

        public Builder userBo(HtmlUserBo userBo) {
            this.userBo = userBo;
            return self;
        }

        public Builder roleBos(List<HtmlRoleBo> roleBos) {
            if (roleBos == null) {
                roleBos = new ArrayList<>();
            }
            this.roleBos = new ArrayList<>(roleBos);
            return self;
        }

        public Builder menuBos(List<HtmlMenuBo> menuBos) {
            if (menuBos == null) {
                menuBos = new ArrayList<>();
            }
            this.menuBos = new ArrayList<>(menuBos);
            this.menuPaths = this.menuBos.stream().map(MenuBo::getMenuPath).collect(Collectors.toSet());
            return self;
        }

        public Builder routers(List<RouterBo> routers) {
            this.routers = routers;
            return self;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return self;
        }

        public Builder loginTime(LocalDateTime loginTime) {
            this.loginTime = loginTime;
            return self;
        }

        public Builder token(String token) {
            this.token = token;
            return self;
        }

        public HtmlLoginUser build() {
            return new HtmlLoginUser(this);
        }
    }
}
