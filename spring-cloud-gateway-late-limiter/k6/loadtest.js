import http from 'k6/http';
import { check, group, sleep } from 'k6';

export const options = {
    duration: '10s',
    vus: '1',
};

const BASE_URL = 'http://localhost:18080';

export default () => {
    const limiter = http.get(`${BASE_URL}/demo?userId=test`);

    check(limiter, {
        'success limitter ': (resp) => resp.status === 200
    });
};
