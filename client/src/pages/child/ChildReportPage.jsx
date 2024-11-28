import React from 'react';
import PaymentReport from '../../components/pages/child/payment/PaymentReport';
import ReceiveMessage from '../../components/pages/child/payment/ReceiveMessage';

const ChildReportPage = () => {
    return (
        <div>
            <PaymentReport />
            <ReceiveMessage />
        </div>
    );
};

export default ChildReportPage;
