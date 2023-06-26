import { OrderProductQuantity } from "./order-quantity.model";

export interface OrderDetails {
    fullName: string,
    fullAddress: string,
    contactNumber: string,
    alternateContactNumber: string,
    transactionId: string,
    orderProductQuantities: OrderProductQuantity[],
}