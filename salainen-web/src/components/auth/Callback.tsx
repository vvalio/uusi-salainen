import { useAuth } from 'react-oidc-context';
import { useNavigate } from 'react-router';

const Callback: React.FC = () => {
  const navigate = useNavigate();
  navigate('/');

  return <div></div>;
};

export default Callback;
