package com.pip.chatbot.repository.food;

import com.pip.chatbot.jooq.food.Tables;
import com.pip.chatbot.model.food.Dish;
import com.pip.chatbot.model.food.Menu;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MenuRepository {
    private final DSLContext dsl;

    public MenuRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<Menu> getMenu(int id) {

        return dsl.select()
                .from(Tables.MENU)
                .leftJoin(Tables.MENU_DISH)
                .on(Tables.MENU.ID.eq(Tables.MENU_DISH.MENU_ID))
                .leftJoin(Tables.DISH)
                .on(Tables.MENU_DISH.DISH_ID.eq(Tables.DISH.ID))
                .where(Tables.MENU.ID.eq(id))
                .fetchGroups(
                        r -> r.into(Tables.MENU).into(Menu.class),
                        r -> r.into(Tables.DISH).into(Dish.class)
                ).entrySet()
                .stream()
                .peek(
                        o -> o.getKey().setDishes(o.getValue()))
                .findAny()
                .map(Map.Entry::getKey);

    }

    public List<Menu> getAllMenus() {

        return dsl.select()
                .from(Tables.MENU)
                .leftJoin(Tables.MENU_DISH)
                .on(Tables.MENU.ID.eq(Tables.MENU_DISH.MENU_ID))
                .leftJoin(Tables.DISH)
                .on(Tables.MENU_DISH.DISH_ID.eq(Tables.DISH.ID))
                .fetchGroups(
                        r -> r.into(Tables.MENU).into(Menu.class),
                        r -> r.into(Tables.DISH).into(Dish.class)
                ).entrySet()
                .stream()
                .peek(
                        o -> o.getKey().setDishes(o.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    public boolean deleteMenu(int id) {
        int numberOfRowsAffected = dsl.delete(Tables.MENU)
                .where(Tables.MENU.ID.eq(id))
                .execute();
        return numberOfRowsAffected >= 1;
    }

    public Menu createMenu(Menu menu) {
        Menu result = dsl.insertInto(Tables.MENU)
                .columns(Tables.MENU.ID, Tables.MENU.RESTAURANT_ID, Tables.MENU.START_DATE, Tables.MENU.END_DATE)
                .values(menu.getId(), menu.getRestaurantId(), menu.getStartDate(), menu.getEndDate())
                .onDuplicateKeyUpdate()
                .set(Tables.MENU.START_DATE, menu.getStartDate())
                .set(Tables.MENU.END_DATE, menu.getEndDate())
                .set(Tables.MENU.RESTAURANT_ID, menu.getRestaurantId())
                .returning()
                .fetchOne()
                .into(Menu.class);

        dsl.delete(Tables.MENU_DISH)
                .where(Tables.MENU_DISH.MENU_ID.eq(menu.getId()))
                .execute();

        List<Dish> dishes = new ArrayList<>();
        for (Dish dish : menu.getDishes()) {
            dishes.add(dsl.insertInto(Tables.DISH)
                    .columns(Tables.DISH.ID, Tables.DISH.DISH_, Tables.DISH.PRICE)
                    .values(dish.getId(), dish.getDish(), dish.getPrice())
                    .onDuplicateKeyUpdate()
                    .set(Tables.DISH.DISH_, dish.getDish())
                    .set(Tables.DISH.PRICE, dish.getPrice())
                    .returning()
                    .fetchOne()
                    .into(Dish.class));

            dsl.insertInto(Tables.MENU_DISH)
                    .set(Tables.MENU_DISH.MENU_ID, menu.getId())
                    .set(Tables.MENU_DISH.DISH_ID, dish.getId())
                    .execute();

        }
        result.setDishes(dishes);

        return result;
    }


}
