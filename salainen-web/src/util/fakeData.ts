import type { types } from '../openapi/client';

export const fakeDataWithKey = <T>(len: number): types.DataWithKey<T>[] => {
  return Array(len)
    .fill({})
    .map((v, i) => {
      console.log('i', i);
      v.key = i;
      return v;
    });
};
