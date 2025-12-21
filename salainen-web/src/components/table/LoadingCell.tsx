import Skeleton from '@mui/material/Skeleton';
import TableCell from '@mui/material/TableCell';
import { createContext, useContext, type PropsWithChildren } from 'react';

export type LoadingCellProps = PropsWithChildren<{
  /**
   * Can override the context with this if needed
   */
  isLoading?: boolean;
}>;

const LoadingCell: React.FC<LoadingCellProps> = ({ isLoading, children }) => {
  const contextSaysLoading = useLoadingCell();
  const showLoading = isLoading ? true : contextSaysLoading;

  return (
    <TableCell>
      {showLoading ? (
        <Skeleton
          variant="rectangular"
          height={15}
          sx={{ borderRadius: '10px' }}
        />
      ) : (
        children
      )}
    </TableCell>
  );
};

export const LoadingCellContext = createContext({ loading: true });

export const useLoadingCell = () => {
  return useContext(LoadingCellContext);
};

export default LoadingCell;
