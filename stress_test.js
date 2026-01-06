import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  vus: 50, // 50 concurrent virtual users
  duration: '30s',
};

export default function () {
  const url = 'http://68.221.196.157:8081/api/segreteria/docenti'; 
  
  http.get(url);
  sleep(1); // Each user waits 1 second before repeating the request
}