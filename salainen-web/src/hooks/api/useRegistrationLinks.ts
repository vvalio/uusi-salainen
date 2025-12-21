import { apiClient, type types } from '../../openapi/client';
import { useEffect, useState } from 'react';

export type UseRegistrationLinksResult = types.ApiResult<
  types.DataWithKey<types.RegistrationLink>[]
>;

const useRegistrationLinks = (): UseRegistrationLinksResult => {
  const [data, setData] = useState<
    types.DataWithKey<types.RegistrationLink>[] | undefined
  >(undefined);
  const [error, setError] = useState<types.ApiError | undefined>(undefined);
  const [status, setStatus] = useState<number | undefined>(undefined);

  useEffect(() => {
    const abortController = new AbortController();
    const fetchData = async () => {
      const {
        data,
        error: fetchError,
        response: { status: responseStatus },
      } = await apiClient.GET('/registration/link/all', {
        signal: abortController.signal,
      });

      setStatus(responseStatus);
      if (fetchError) {
        setError(fetchError as types.ApiError);
      } else {
        setData(data!.map(entry => ({ ...entry, key: entry.slug! })));
      }
    };

    fetchData();
    return () => abortController.abort();
  }, []);

  return {
    data,
    error,
    responseStatus: status,
    isPending: !status, // if no status fetched yet
    isError: !!error,
  };
};

export default useRegistrationLinks;
