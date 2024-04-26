package com.octanepvp.splityosis.octaneengine.function.mathfunction;

public abstract class ConditionalOperator {

    public static ConditionalOperator BIGGER = new ConditionalOperator() {
        @Override
        public boolean check(double left, double right) {
            return left > right;
        }
    };

    public static ConditionalOperator BIGGER_EQUALS = new ConditionalOperator() {
        @Override
        public boolean check(double left, double right) {
            return left >= right;
        }
    };

    public static ConditionalOperator SMALLER = new ConditionalOperator() {
        @Override
        public boolean check(double left, double right) {
            return left < right;
        }
    };

    public static ConditionalOperator SMALLER_EQUALS = new ConditionalOperator() {
        @Override
        public boolean check(double left, double right) {
            return left <= right;
        }
    };

    public static ConditionalOperator EQUALS = new ConditionalOperator() {
        @Override
        public boolean check(double left, double right) {
            return left == right;
        }
    };

    public abstract boolean check(double left, double right);
}
