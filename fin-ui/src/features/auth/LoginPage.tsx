import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../../api/auth.api";

const LoginPage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const data = await login({ email, password });

      localStorage.setItem("token", data.token);
      navigate("/");
    } catch (err: any) {
      setError(err.message || "Login failed");
      setLoading(false);
    }
  };

  return (
    <div className="grid grid-cols-10 min-h-screen w-full">
      {/* LEFT SIDE (60%) - col-span-6 */}
      <div className="col-span-6 bg-gray-100 flex flex-col items-center justify-center p-10">
        <h1 className="text-6xl font-bold text-primary mb-4">FinSight</h1>
        <p className="text-gray-600 text-xl">
          Smart financial insights at your fingertips.
        </p>
      </div>

      {/* RIGHT SIDE (40%) - col-span-4 */}
      <div className="col-span-4 flex items-center justify-center p-8 bg-white">
        <div className="w-full max-w-md">
          <h2 className="text-3xl font-semibold mb-8 text-gray-800">Login</h2>

          <form className="flex flex-col gap-6" onSubmit={handleSubmit}>
            {/* Email Field */}
            <div className="flex flex-col gap-2">
              <label htmlFor="email" className="font-medium text-gray-700">
                Email
              </label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                required
                placeholder="Enter your email"
              />
            </div>

            {/* Password Field */}
            <div className="flex flex-col gap-2">
              <label htmlFor="password" className="font-medium text-gray-700">
                Password
              </label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                required
                placeholder="Enter your password"
              />
            </div>

            {error && <p className="text-red-400 text-sm">{error}</p>}

            {/* Submit Button */}
            <button
              type="submit"
              disabled={loading}
              className="bg-primary rounded-2xl p-3 text-white font-bold hover:bg-cyanDark transition-all mt-4"
            >
              {loading ? "Logging in..." : "Login"}
            </button>
          </form>

          {/* Register Link */}
          <p className="text-center text-gray-600 mt-6">
            Don’t have an account?{" "}
            <Link
              to="/register"
              className="text-primary font-semibold hover:underline"
            >
              Register here
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
