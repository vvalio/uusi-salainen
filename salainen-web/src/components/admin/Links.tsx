import useRegistrationLinks from '../../hooks/api/useRegistrationLinks';
import Table from '@mui/material/Table';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import Paper from '@mui/material/Paper';
import TableBody from '@mui/material/TableBody';
import LoadingCell, { LoadingCellContext } from '../table/LoadingCell';
import { useEffect, useState } from 'react';
import { fakeDataWithKey } from '../../util/fakeData';
import type { types } from '../../openapi/client';

const Links: React.FC = () => {
  const linksResult = useRegistrationLinks();
  const [loading, setLoading] = useState(true);

  // Either a fake data object for the skeleton or the actual data
  const [data, setData] = useState<types.DataWithKey<types.RegistrationLink>[]>(
    fakeDataWithKey(5)
  );

  useEffect(() => {
    if (!linksResult.isPending) {
      setLoading(false);
      if (!linksResult.isError) {
        setData(linksResult.data!);
      }
    }
  }, [linksResult.isPending]);

  return (
    <>
      <LoadingCellContext.Provider value={{ loading }}>
        {linksResult.data && (
          <TableContainer component={Paper} sx={{ width: '60%' }}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>URL</TableCell>
                  <TableCell>Created at</TableCell>
                  <TableCell>Used</TableCell>
                  <TableCell>Registered user</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {data.map(entry => (
                  <TableRow key={entry.key}>
                    <LoadingCell>{`https://salainen.valiov.dev/rlink/${entry.slug}`}</LoadingCell>
                    <LoadingCell>{entry.createdAt}</LoadingCell>
                    <LoadingCell>-</LoadingCell>
                    <LoadingCell>-</LoadingCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        )}
      </LoadingCellContext.Provider>
    </>
  );
};

export default Links;
