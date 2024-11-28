// 날짜 배열을 YYYY-MM-DD 형태로 변환하는 함수
export const formatDate = (dateArray) => {
    if (dateArray && dateArray.length === 3) {
        const [year, month, day] = dateArray;
        return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
    }
    return '';
};

// 금액을 천 단위로 포맷하는 함수
export const formatCurrency = (amount) => {
    return new Intl.NumberFormat('ko-KR').format(amount);
};
