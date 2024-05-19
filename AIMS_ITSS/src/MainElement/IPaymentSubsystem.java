package MainElement;

import VNPaySubsystem.Exception.PaymentException;

public interface IPaymentSubsystem {

	public abstract PaymentTransaction payOrder(Order order, String contents) throws PaymentException;

}
