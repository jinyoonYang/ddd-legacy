package kitchenpos.application;

import kitchenpos.config.MenuGroupTestContextConfiguration;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static kitchenpos.MenuGroupTestHelper.메뉴카테고리_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@Import(MenuGroupTestContextConfiguration.class)
class MenuGroupServiceTest {
    @Autowired
    private MenuGroupService menuGroupService;
    @Autowired
    private MenuGroupRepository menuGroupRepository;
    private MenuGroup 추천메뉴;
    private MenuGroup 원플러스원메뉴;
    private List<MenuGroup> 메뉴카테고리들 = new ArrayList<>();


    @BeforeEach
    void setUp() {
        추천메뉴 = 메뉴카테고리_생성("추천메뉴");
        원플러스원메뉴 = 메뉴카테고리_생성("원플러스원메뉴");
        메뉴카테고리들.add(추천메뉴);
        메뉴카테고리들.add(원플러스원메뉴);

        Mockito.when(menuGroupRepository.save(any()))
                .thenReturn(추천메뉴);

        Mockito.when(menuGroupRepository.findAll())
                .thenReturn(메뉴카테고리들);
    }

    @DisplayName("메뉴 카테고리를 생성한다.")
    @Test
    void createMenuGroup(){
        MenuGroup createMenuGroup = menuGroupService.create(추천메뉴);
        assertThat(createMenuGroup.getName()).isSameAs(추천메뉴.getName());
    }

    @DisplayName("모든 메뉴 카테고리를 조회한다.")
    @Test
    void getAllMenuGroup(){
        List<MenuGroup> menuGroupList = menuGroupService.findAll();
        assertThat(menuGroupList.size()).isSameAs(메뉴카테고리들.size());
    }

    @DisplayName("메뉴 카테고리 이름을 빈값을 전달하는 경우 IllegalArgumentException 예외가 발생한다. ")
    @ParameterizedTest
    @NullAndEmptySource
    void emptyOrnullMenuGroupName(String name){
        MenuGroup requestMenuGroup = new MenuGroup();
        requestMenuGroup.setName(name);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> menuGroupService.create(requestMenuGroup));
    }
}