import axios from 'axios'
import { mockAdapter } from '../mock/adapter'

const request = axios.create({ baseURL: import.meta.env.VITE_API_BASE_URL || '/api', timeout: 10000 })
request.interceptors.response.use((response) => response.data, (error) => Promise.reject(error))

export const apiRequest = (config) => (
  import.meta.env.VITE_USE_MOCK !== 'false' ? mockAdapter(config) : request(config)
)
export default request
