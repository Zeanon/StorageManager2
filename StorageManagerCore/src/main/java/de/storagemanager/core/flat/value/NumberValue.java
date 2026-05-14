package de.storagemanager.core.flat.value;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public record NumberValue(Number value) implements Value {

    public <T extends Number> boolean isNumber(Type<T> type) {
        return type.convert(value).isPresent();
    }

    public <T extends Number> @NotNull Optional<@NotNull T> asNumber(Type<T> type) {
        return type.convert(value);
    }

    public interface Type<T extends Number> {
        Type<Byte> Byte = new Type<>() {

            public static final byte MAX_VALUE = java.lang.Byte.MAX_VALUE;
            public static final byte MIN_VALUE = java.lang.Byte.MIN_VALUE;

            public static final BigInteger BIG_INTEGER_MAX_VALUE = BigInteger.valueOf(MAX_VALUE);
            public static final BigInteger BIG_INTEGER_MIN_VALUE = BigInteger.valueOf(MIN_VALUE);

            @Override
            public @NotNull Optional<@NotNull Byte> convert(@NonNull Number number) {
                return switch (number) {
                    case Byte value -> Optional.of(value);
                    case Short value when value >= MIN_VALUE && value <= MAX_VALUE -> Optional.of(value.byteValue());
                    case Integer value when value >= MIN_VALUE && value <= MAX_VALUE -> Optional.of(value.byteValue());
                    case Long value when value >= MIN_VALUE && value <= MAX_VALUE -> Optional.of(value.byteValue());
                    case BigInteger value when value.compareTo(BIG_INTEGER_MIN_VALUE) >= 0 && value.compareTo(BIG_INTEGER_MAX_VALUE) <= 0 ->
                            Optional.of(value.byteValue());
                    case Float value when value == (float) value.intValue() && value >= MIN_VALUE && value <= MAX_VALUE ->
                            Optional.of(value.byteValue());
                    case Double value when value == (float) value.intValue() && value >= MIN_VALUE && value <= MAX_VALUE ->
                            Optional.of(value.byteValue());
                    case BigDecimal value -> convertFromBigDecimal(value);
                    default -> Optional.empty();
                };
            }

            private Optional<Byte> convertFromBigDecimal(BigDecimal value) {
                try {
                    return Optional.of(value.byteValueExact());
                } catch (ArithmeticException e) {
                    return Optional.empty();
                }
            }
        };
        Type<Short> Short = new Type<>() {
            public static final short MAX_VALUE = java.lang.Short.MAX_VALUE;
            public static final short MIN_VALUE = java.lang.Short.MIN_VALUE;

            public static final BigInteger BIG_INTEGER_MAX_VALUE = BigInteger.valueOf(MAX_VALUE);
            public static final BigInteger BIG_INTEGER_MIN_VALUE = BigInteger.valueOf(MIN_VALUE);

            @Override
            public @NotNull Optional<@NotNull Short> convert(@NonNull Number number) {
                return switch (number) {
                    case Byte value -> Optional.of(value.shortValue());
                    case Short value -> Optional.of(value);
                    case Integer value when value >= MIN_VALUE && value <= MAX_VALUE -> Optional.of(value.shortValue());
                    case Long value when value >= MIN_VALUE && value <= MAX_VALUE -> Optional.of(value.shortValue());
                    case BigInteger value when value.compareTo(BIG_INTEGER_MIN_VALUE) >= 0 && value.compareTo(BIG_INTEGER_MAX_VALUE) <= 0 ->
                            Optional.of(value.shortValue());
                    case Float value when value == (float) value.intValue() && value >= MIN_VALUE && value <= MAX_VALUE ->
                            Optional.of(value.shortValue());
                    case Double value when value == (float) value.intValue() && value >= MIN_VALUE && value <= MAX_VALUE ->
                            Optional.of(value.shortValue());
                    case BigDecimal value -> convertFromBigDecimal(value);
                    default -> Optional.empty();
                };
            }

            private Optional<Short> convertFromBigDecimal(BigDecimal value) {
                try {
                    return Optional.of(value.shortValueExact());
                } catch (ArithmeticException e) {
                    return Optional.empty();
                }
            }
        };
        Type<Integer> Int = new Type<>() {
            public static final int MAX_VALUE = java.lang.Integer.MAX_VALUE;
            public static final int MIN_VALUE = java.lang.Integer.MIN_VALUE;

            public static final BigInteger BIG_INTEGER_MAX_VALUE = BigInteger.valueOf(MAX_VALUE);
            public static final BigInteger BIG_INTEGER_MIN_VALUE = BigInteger.valueOf(MIN_VALUE);

            @Override
            public @NotNull Optional<@NotNull Integer> convert(@NonNull Number number) {
                return switch (number) {
                    case Byte value -> Optional.of(value.intValue());
                    case Short value -> Optional.of(value.intValue());
                    case Integer value -> Optional.of(value);
                    case Long value when value >= MIN_VALUE && value <= MAX_VALUE -> Optional.of(value.intValue());
                    case BigInteger value when value.compareTo(BIG_INTEGER_MIN_VALUE) >= 0 && value.compareTo(BIG_INTEGER_MAX_VALUE) <= 0 ->
                            Optional.of(value.intValue());
                    case Float value when value == (float) value.intValue() -> Optional.of(value.intValue());
                    case Double value when value == (float) value.intValue() && value >= MIN_VALUE && value <= MAX_VALUE ->
                            Optional.of(value.intValue());
                    case BigDecimal value -> convertFromBigDecimal(value);
                    default -> Optional.empty();
                };
            }

            private Optional<Integer> convertFromBigDecimal(BigDecimal value) {
                try {
                    return Optional.of(value.intValueExact());
                } catch (ArithmeticException e) {
                    return Optional.empty();
                }
            }
        };
        Type<Long> Long = new Type<>() {
            public static final long MAX_VALUE = java.lang.Long.MAX_VALUE;
            public static final long MIN_VALUE = java.lang.Long.MIN_VALUE;

            public static final BigInteger BIG_INTEGER_MAX_VALUE = BigInteger.valueOf(MAX_VALUE);
            public static final BigInteger BIG_INTEGER_MIN_VALUE = BigInteger.valueOf(MIN_VALUE);

            @Override
            public @NotNull Optional<@NotNull Long> convert(@NonNull Number number) {
                return switch (number) {
                    case Byte value -> Optional.of(value.longValue());
                    case Short value -> Optional.of(value.longValue());
                    case Integer value -> Optional.of(value.longValue());
                    case Long value -> Optional.of(value);
                    case BigInteger value when value.compareTo(BIG_INTEGER_MIN_VALUE) >= 0 && value.compareTo(BIG_INTEGER_MAX_VALUE) <= 0 ->
                            Optional.of(value.longValue());
                    case Float value when value == (float) value.longValue() -> Optional.of(value.longValue());
                    case Double value when value == (float) value.longValue() && value >= MIN_VALUE && value <= MAX_VALUE ->
                            Optional.of(value.longValue());
                    case BigDecimal value -> convertFromBigDecimal(value);
                    default -> Optional.empty();
                };
            }

            private Optional<Long> convertFromBigDecimal(BigDecimal value) {
                try {
                    return Optional.of(value.longValueExact());
                } catch (ArithmeticException e) {
                    return Optional.empty();
                }
            }
        };
        Type<BigInteger> BigInt = new Type<>() {
            @Override
            public @NotNull Optional<@NotNull BigInteger> convert(@NonNull Number number) {
                return switch (number) {
                    case Byte value -> Optional.of(BigInteger.valueOf(value.longValue()));
                    case Short value -> Optional.of(BigInteger.valueOf(value.longValue()));
                    case Integer value -> Optional.of(BigInteger.valueOf(value.longValue()));
                    case Long value -> Optional.of(BigInteger.valueOf(value));
                    case BigInteger value -> Optional.of(value);
                    case Float value when value == (float) value.longValue() ->
                            Optional.of(BigInteger.valueOf(value.longValue()));
                    case Double value when value == (float) value.longValue() ->
                            Optional.of(BigInteger.valueOf(value.longValue()));
                    case BigDecimal value -> convertFromBigDecimal(value);
                    default -> Optional.empty();
                };
            }

            private Optional<BigInteger> convertFromBigDecimal(BigDecimal value) {
                try {
                    return Optional.of(value.toBigIntegerExact());
                } catch (ArithmeticException e) {
                    return Optional.empty();
                }
            }
        };

        Type<Float> Float = new Type<>() {
            public static final float MAX_VALUE = java.lang.Float.MAX_VALUE;
            public static final float MIN_VALUE = java.lang.Float.MIN_VALUE;

            public static final BigDecimal BIG_DECIMAL_MAX_VALUE = BigDecimal.valueOf(MAX_VALUE);
            public static final BigDecimal BIG_DECIMAL_MIN_VALUE = BigDecimal.valueOf(MIN_VALUE);

            @Override
            public @NotNull Optional<@NotNull Float> convert(@NonNull Number number) {
                return switch (number) {
                    case Byte value -> Optional.of(value.floatValue());
                    case Short value -> Optional.of(value.floatValue());
                    case Integer value -> Optional.of(value.floatValue());
                    case Long value when value >= MIN_VALUE -> Optional.of(value.floatValue());
                    case BigInteger value -> Optional.of(value.floatValue()); // TODO: Check Size constraints
                    case Float value -> Optional.of(value);
                    case Double value when value >= MIN_VALUE && value <= MAX_VALUE -> Optional.of(value.floatValue());
                    case BigDecimal value when value.compareTo(BIG_DECIMAL_MIN_VALUE) >= 0 && value.compareTo(BIG_DECIMAL_MAX_VALUE) <= 0 ->
                            Optional.of(value.floatValue());
                    default -> Optional.empty();
                };
            }
        };
        Type<Double> Double = new Type<>() {
            public static final double MAX_VALUE = java.lang.Double.MAX_VALUE;
            public static final double MIN_VALUE = java.lang.Double.MIN_VALUE;

            public static final BigDecimal BIG_DECIMAL_MAX_VALUE = BigDecimal.valueOf(MAX_VALUE);
            public static final BigDecimal BIG_DECIMAL_MIN_VALUE = BigDecimal.valueOf(MIN_VALUE);

            @Override
            public @NotNull Optional<@NotNull Double> convert(@NonNull Number number) {
                return switch (number) {
                    case Byte value -> Optional.of(value.doubleValue());
                    case Short value -> Optional.of(value.doubleValue());
                    case Integer value -> Optional.of(value.doubleValue());
                    case Long value when value >= MIN_VALUE -> Optional.of(value.doubleValue());
                    case BigInteger value -> Optional.of(value.doubleValue()); // TODO: Check Size constraints
                    case Float value -> Optional.of(value.doubleValue());
                    case Double value -> Optional.of(value);
                    case BigDecimal value when value.compareTo(BIG_DECIMAL_MIN_VALUE) >= 0 && value.compareTo(BIG_DECIMAL_MAX_VALUE) <= 0 ->
                            Optional.of(value.doubleValue());
                    default -> Optional.empty();
                };
            }
        };
        Type<BigDecimal> BigFloat = new Type<>() {
            @Override
            public @NotNull Optional<@NotNull BigDecimal> convert(@NonNull Number number) {
                return switch (number) {
                    case Byte value -> Optional.of(BigDecimal.valueOf(value));
                    case Short value -> Optional.of(BigDecimal.valueOf(value));
                    case Integer value -> Optional.of(BigDecimal.valueOf(value));
                    case Long value -> Optional.of(BigDecimal.valueOf(value));
                    case BigInteger value -> Optional.of(new BigDecimal(value));
                    case Float value -> Optional.of(BigDecimal.valueOf(value));
                    case Double value -> Optional.of(BigDecimal.valueOf(value));
                    case BigDecimal value -> Optional.of(value);
                    default -> Optional.empty();
                };
            }
        };

        @NotNull Optional<@NotNull T> convert(@NonNull Number number);
    }

    static void main() {
        NumberValue number = new NumberValue(BigDecimal.valueOf(0));
        System.out.println(number);
        System.out.println(number.asNumber(Type.Float));
    }
}
