package enums;

public enum MsgStatusEnum {
    SUCCESS(1),		// 发布成功
    FORBID(2);		// 禁止播放，管理员操作

    public final int value;

    MsgStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
