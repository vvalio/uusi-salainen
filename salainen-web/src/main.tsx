import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './components/App';
import { AuthProvider, type AuthProviderProps } from 'react-oidc-context';
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

i18n
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    fallbackLng: 'en',
    resources: {
      en: {},
      fi: {},
    },
    interpolation: {
      escapeValue: false, // react already safes from xss
    },
  });

const authSettings: Partial<AuthProviderProps> = {
  authority: 'http://localhost:8080/realms/salainen',
  client_id: 'salainen_app',
  response_type: 'code',
  scope: 'openid profile email',
};

createRoot(document.getElementById('root')!).render(
  <AuthProvider {...authSettings}>
    <StrictMode>
      <App />
    </StrictMode>
  </AuthProvider>
);
