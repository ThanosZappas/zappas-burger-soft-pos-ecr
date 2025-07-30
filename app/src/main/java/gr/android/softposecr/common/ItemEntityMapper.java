package gr.android.softposecr.common;

import java.util.List;
import java.util.stream.Collectors;

import gr.android.softposecr.data.database.ItemEntity;
import gr.android.softposecr.domain.models.ItemUi;

public class ItemEntityMapper {

    public static ItemUi mapToItemUi(ItemEntity item) {
        return new ItemUi(
                item.getId(),
                item.getTitle(),
                item.getOverview(),
                item.getPosterPath(),
                item.getVoteAverage(),
                item.getBackdropPath(),
                item.getReleaseDate(),
                item.getPrice()
        );
    }

    public static List<ItemUi> mapToUiItemList(List<ItemEntity> itemEntities) {
        return itemEntities.stream()
                .map(ItemEntityMapper::mapToItemUi)
                .collect(Collectors.toList());
    }
}
