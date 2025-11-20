package org.backend.gregsgamesbackend.dto.response;

public class OrderItemResponseDTO {

    private Long gameId;
    private Integer quantity;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
